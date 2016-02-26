package com.jvra.ocache.es.script;

import com.jvra.ocache.es.CacheProviders;

/**
 * Created by Jansel Valentin on 8/20/2015.
 */
final class CacheIdiomInjector {
    static{
        CacheProviders.registerDefault(new ScriptCacheIdiomProvider());
    }
}
