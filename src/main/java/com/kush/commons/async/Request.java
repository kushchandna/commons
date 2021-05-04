package com.kush.commons.async;

public interface Request<T> {

    T process() throws RequestFailedException;
}
