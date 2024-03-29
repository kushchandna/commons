package com.kush.commons.async;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

class LocalResponse<T> implements Response<T> {

    private final CountDownLatch latch;

    private T result;
    private boolean resultAvailable = false;
    private RequestFailedException error;

    private final List<ResultListener<T>> resultListeners = new ArrayList<>();
    private final List<ErrorListener> errorListeners = new ArrayList<>();

    LocalResponse() {
        latch = new CountDownLatch(1);
    }

    @Override
    public void await() throws InterruptedException, RequestFailedException {
        latch.await();
        if (error != null) {
            throw new RequestFailedException(error);
        }
    }

    @Override
    public T getResult() throws RequestFailedException, InterruptedException {
        await();
        return result;
    }

    @Override
    public void addResultListener(ResultListener<T> resultListener) {
        resultListeners.add(resultListener);
        if (resultAvailable) {
            resultListener.onResult(result);
        }
    }

    @Override
    public void addErrorListener(ErrorListener errorListener) {
        errorListeners.add(errorListener);
        if (error != null) {
            errorListener.onError(error);
        }
    }

    @Override
    public void removeListeners() {
        resultListeners.clear();
        errorListeners.clear();
    }

    void setResult(T result) {
        if (latch.getCount() == 0) {
            return;
        }
        this.result = result;
        resultAvailable = true;
        notifyResult(result);
        latch.countDown();
    }

    void setError(RequestFailedException error) {
        if (latch.getCount() == 0) {
            return;
        }
        this.error = error;
        notifyError(error);
        latch.countDown();
    }

    private void notifyResult(T result) {
        for (ResultListener<T> listener : resultListeners) {
            listener.onResult(result);
        }
    }

    private void notifyError(RequestFailedException error) {
        for (ErrorListener listener : errorListeners) {
            listener.onError(error);
        }
    }
}
