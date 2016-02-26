package com.jvra.ocache.es.script

import com.jvra.ocache.es.CacheOperation
import com.jvra.ocache.es.OperationExecutionException
import groovy.transform.PackageScope
import org.apache.log4j.Logger

/**
 *
 * Created by Jansel Valentin on 08/22/15.
 **/


@PackageScope
class FirstClassOperation implements CacheOperation{

    private static final Logger logger = Logger.getLogger( FirstClassOperation.class )

    private Script script;


    private CacheOperation proxyOperation;

    def equal = {
        "="
    }

    FirstClassOperation(Script script) {
        this.script = script
    }

    def clean(what){
        [where:{ field ->
            [iss: { condition ->
                [to: { value ->
//                    return "clean from ${what} where ${field} ${condition()} ${value}"
                    proxyOperation = new CleanOperation(docType: what,field:field,condition:condition(),value:value)
                    proxyOperation.perform()
                }]
            }]
        }]
    }

    @Override
    Object perform() throws OperationExecutionException{
        if( null == script ){
            logger.warn( "Something terrible was happened, script was null " )
            return null;
        }

        try {
            def binding = new Binding()
            binding.elasticsearch = { closure ->
                closure.delegate = this
                closure()
            }

            script.binding = binding;
            script.run()
        }catch ( Exception ex ){
            logger.error(ex)
            throw new OperationExecutionException(ex)
        }
        return null;
    }

    @Override
    void recycle() {
        if( null != proxyOperation )
            proxyOperation.recycle()
        script = null;
    }
}
