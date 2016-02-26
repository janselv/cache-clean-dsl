package com.jvra.ocache.es.script;

import com.jvra.ocache.es.CacheIdiom;
import com.jvra.ocache.es.CacheIdiomProvider;

/**
 * Created by Jansel Valentin on 08/21/15.
 **/
class ScriptCacheIdiomProvider implements CacheIdiomProvider{
    @Override
    public CacheIdiom createIdiom() {
        return ScriptCacheIdiom.getSingle();
    }
}

