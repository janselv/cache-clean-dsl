package com.jvra.ocache.es;

import com.jvra.ocache.debug.BuildConfig;

/**
 * Created by Jansel Valentin on 08/21/15.
 **/
public final class CacheProviders {

    private static CacheIdiomProvider mDefault;

    public static void registerDefault( CacheIdiomProvider cacheIdiom ){

        boolean manyProvider = false;

        try{
            assert (manyProvider = BuildConfig.ALLOW_MANY_PROVIDERS);
        }catch( AssertionError e ){
            //expected
            e.printStackTrace();
        }

        if( null != mDefault && !manyProvider )
            throw new IllegalStateException("Provider has been set");

        mDefault = cacheIdiom;
    }

    public static CacheIdiomProvider getDefault(){
        return mDefault;
    }
}
