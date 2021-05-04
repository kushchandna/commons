package com.kush.commons.async;

public class RequestFailedException extends Exception {

    private static final long serialVersionUID = 1L;

    public RequestFailedException() {
    }

    public RequestFailedException(Throwable e) {
        super(e.getMessage(), e);
    }
}
