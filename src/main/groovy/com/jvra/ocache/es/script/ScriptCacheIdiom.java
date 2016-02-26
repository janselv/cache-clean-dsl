package com.jvra.ocache.es.script;

import com.jvra.ocache.es.CacheIdiom;
import com.jvra.ocache.es.CacheOperation;
import com.jvra.ocache.es.OperationInterpretExecption;
import com.jvra.ocache.es.script.lru.LRUCache;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import org.apache.log4j.Logger;

/**
 * Created by Jansel Valentin on 8/24/2015.
 */
class ScriptCacheIdiom implements CacheIdiom{
    private static final Logger logger = Logger.getLogger( ScriptCacheIdiom.class );

    private static final int LRU_CAPACITY = 70;

    private static GroovyShell shell = new GroovyShell();

    private static final LRUCache<Integer,Script> scriptCache = new LRUCache<Integer, Script>(LRU_CAPACITY);


    static final ScriptCacheIdiom single = new ScriptCacheIdiom();


    private ScriptCacheIdiom() {
    }

    public static final ScriptCacheIdiom getSingle(){
        return single;
    }


    @Override
    public CacheOperation interpret(String op) throws OperationInterpretExecption {
        logger.info( "Interpreting operation: "+op );
        try {
            setupShell();

            if (null != shell) {
                final int lScriptHash = System.identityHashCode(op.trim().intern());

                Script script;
                synchronized ( scriptCache ) {
                    script = scriptCache.get(lScriptHash);
                }

                if( null == script ){
                    synchronized ( scriptCache ) {
                        script = shell.parse(op);
                        scriptCache.set(lScriptHash, script);
                    }
                }

                if (null != script) {
                    return new FirstClassOperation(script);
                } else {
                    logger.warn("No possible to resolve script for operation: " + op);
                }
            }
        }catch( Exception ex ){
            logger.error( ex );
            throw new OperationInterpretExecption(ex);
        }

        return NoOperation.OP;
    }


    private static void setupShell(){
        if( null == shell ){
            shell = new GroovyShell();
        }
    }
}
