package com.kush.commons.beta.arrow;

import org.apache.arrow.vector.VectorSchemaRoot;
import org.apache.arrow.vector.types.pojo.Schema;

public interface Vectorizer<T> {
    
    Schema getSchema();

    void vectorize(int index, T value, VectorSchemaRoot schemaRoot);
}
