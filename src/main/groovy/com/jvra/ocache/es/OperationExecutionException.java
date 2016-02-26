package com.jvra.ocache.es;

/**
 * Created by Jansel Valentin on 08/21/15.
 **/
public class OperationExecutionException extends Exception{
    public OperationExecutionException(String message) {
        super(message);
    }

    public OperationExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public OperationExecutionException(Throwable cause) {
        super(cause);
    }

    public OperationExecutionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
