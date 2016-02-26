package com.jvra.ocache.es;

/**
 * Created by Jansel Valentin on 8/20/2015.
 */
public interface CacheOperation {
    Object perform() throws OperationExecutionException;

    void recycle();
}
