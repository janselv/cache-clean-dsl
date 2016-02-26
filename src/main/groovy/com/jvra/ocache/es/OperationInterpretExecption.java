package com.jvra.ocache.es;

/**
 * Created by Jansel Valentin on 8/23/2015.
 */
public class OperationInterpretExecption extends Exception{
    public OperationInterpretExecption() {
    }

    public OperationInterpretExecption(String message) {
        super(message);
    }

    public OperationInterpretExecption(String message, Throwable cause) {
        super(message, cause);
    }

    public OperationInterpretExecption(Throwable cause) {
        super(cause);
    }

    public OperationInterpretExecption(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
