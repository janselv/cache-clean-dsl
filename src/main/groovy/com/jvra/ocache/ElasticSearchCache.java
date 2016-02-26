package com.jvra.ocache;


import com.jvra.ocache.debug.BuildConfig;
import com.jvra.ocache.es.CacheIdiom;
import com.jvra.ocache.es.CacheIdiomProvider;
import com.jvra.ocache.es.CacheOperation;
import com.jvra.ocache.es.CacheProviders;
import org.apache.log4j.Logger;
import org.gearman.GearmanFunction;
import org.gearman.GearmanFunctionCallback;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Jansel Valentin on 8/20/2015.
 *
 */
public class ElasticSearchCache implements GearmanFunction{

    static {
        try {
            Class.forName( "com.jvra.ocache.es.script.CacheIdiomInjector" ); //Configure implementation provider on function load
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static Logger logger = Logger.getLogger( ElasticSearchCache.class);

    private static final int MAX_THREAD = 10;
    private static final byte[] NO_RESPONSE = new byte[0];

    private static final ExecutorService exec = Executors.newFixedThreadPool(MAX_THREAD);

    @Override
    public byte[] work(String function, final byte[] data, GearmanFunctionCallback callback) throws Exception {
        if( null == data || 0>= data.length ){
            logger.warn( "Missing Params from function call" );
            return NO_RESPONSE;
        }

        boolean runAsyn = true;

        try{
            assert (runAsyn = BuildConfig.CACHE_FUNCTION_THREAD_ENABLED);
        }catch ( AssertionError e ){
            e.printStackTrace();
        }

        if( runAsyn ) {
            exec.execute(new Runnable() {
                final byte[] nData = data;

                @Override
                public void run() {
                    handleMessage(nData);
                }
            });
        }else{
            handleMessage(data);
        }

        return NO_RESPONSE;
    }


    private void handleMessage( byte[] data ){
        try {
            final String action = new String( data );

            logger.info( "Trying to perform elastic search cache operation: "+action );

            CacheIdiomProvider provider = CacheProviders.getDefault();
            CacheIdiom idiom = provider.createIdiom();
            CacheOperation op = idiom.interpret(action);
            op.perform();
            op.recycle();

        }catch ( Exception ex ){
            logger.error( "",ex );
        }
    }
}
