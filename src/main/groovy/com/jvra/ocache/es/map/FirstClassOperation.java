package com.jvra.ocache.es.map;

import com.jvra.ocache.es.CacheOperation;
import com.jvra.ocache.es.OperationExecutionException;

/**
 * Created by Jansel Valentin on 8/24/2015.
 */
public class FirstClassOperation implements CacheOperation{
    @Override
    public Object perform() throws OperationExecutionException {
        return null;
    }

    @Override
    public void recycle() {

    }
}
