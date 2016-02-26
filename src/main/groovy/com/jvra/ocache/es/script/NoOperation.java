package com.jvra.ocache.es.script;

import com.jvra.ocache.es.CacheOperation;

/**
 * Created by Jansel Valentin on 8/24/2015.
 */
class NoOperation implements CacheOperation{
    public static final NoOperation OP = new NoOperation();

    private NoOperation(){
    }

    @Override
    public Object perform() {
        return null;
    }

    @Override
    public void recycle() {

    }
}
