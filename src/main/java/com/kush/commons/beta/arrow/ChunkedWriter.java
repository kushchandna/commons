package com.kush.commons.beta.arrow;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.arrow.memory.RootAllocator;
import org.apache.arrow.vector.VectorSchemaRoot;
import org.apache.arrow.vector.dictionary.DictionaryProvider;
import org.apache.arrow.vector.ipc.ArrowFileWriter;

public class ChunkedWriter<T> {

    private final String file;
    private final Vectorizer<T> vectorizer;
    private final int chunkSize;

    public ChunkedWriter(String file, Vectorizer<T> vectorizer, int chunkSize) {
        this.file = file;
        this.vectorizer = vectorizer;
        this.chunkSize = chunkSize;
    }

    public void write(@SuppressWarnings("unchecked") T... values) {
        try {
            writeObjects(file, values);
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    private void writeObjects(String file, @SuppressWarnings("unchecked") T... values) throws IOException {

        DictionaryProvider.MapDictionaryProvider dictProvider = new DictionaryProvider.MapDictionaryProvider();

        try (RootAllocator allocator = new RootAllocator();
                VectorSchemaRoot schemaRoot = createSchemaRoot(allocator);
                FileOutputStream fd = new FileOutputStream(file);
                ArrowFileWriter fileWriter = new ArrowFileWriter(schemaRoot, dictProvider, fd.getChannel())) {
            fileWriter.start();

            int index = 0;
            while (index < values.length) {
                schemaRoot.allocateNew();
                int chunkIndex = 0;
                while (chunkIndex < chunkSize && index + chunkIndex < values.length) {
                    vectorizer.vectorize(chunkIndex, values[index + chunkIndex], schemaRoot);
                    chunkIndex++;
                }
                schemaRoot.setRowCount(chunkIndex);
                fileWriter.writeBatch();

                index += chunkIndex;
                schemaRoot.clear();
            }
            fileWriter.end();
        }
    }

    private VectorSchemaRoot createSchemaRoot(RootAllocator allocator) {
        return VectorSchemaRoot.create(vectorizer.getSchema(), allocator);
    }
}
