package com.johannag.tapup.globals.application.useCases;

import com.johannag.tapup.globals.infrastructure.utils.Logger;
import org.springframework.data.domain.Page;

import java.util.function.Function;

public class BatchProcessor<T, R> {
    private final int batchSize;
    private final Function<Integer, Page<T>> fetchFunction;
    private final Function<Page<T>, R> processFunction;
    private static final Logger logger = Logger.getLogger(BatchProcessor.class);

    public BatchProcessor(int batchSize, Function<Integer, Page<T>> fetchFunction, Function<Page<T>, R> processFunction) {
        this.batchSize = batchSize;
        this.fetchFunction = fetchFunction;
        this.processFunction = processFunction;
    }

    public R execute() {
        int currentPage = 0;
        R result = null;
        Page<T> batch;

        do {
            try {
                batch = fetchFunction.apply(currentPage);
                if (!batch.isEmpty()) {
                    result = processFunction.apply(batch);
                    currentPage++;
                } else {
                    break;
                }
            } catch (Exception e) {
                logger.error("An error occurred while processing batch at page {}. Error: {}", currentPage, e.getMessage());
                break;
            }
        } while (true);

        return result;
    }
}
