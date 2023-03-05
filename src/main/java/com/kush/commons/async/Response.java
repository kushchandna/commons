package com.kush.commons.async;

public interface Response<T> {

    T getResult() throws RequestFailedException, InterruptedException;

    void await() throws InterruptedException, RequestFailedException;

    void addResultListener(ResultListener<T> resultListener);

    void addErrorListener(ErrorListener errorListener);

    void removeListeners();

    public static interface ResultListener<R> {

        void onResult(R result);
    }

    public static interface ErrorListener {

        void onError(RequestFailedException error);
    }
}
