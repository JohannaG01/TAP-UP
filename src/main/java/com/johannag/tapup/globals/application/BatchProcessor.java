package com.johannag.tapup.globals.application;

import com.johannag.tapup.globals.infrastructure.utils.Logger;
import org.springframework.data.domain.Page;

import java.util.function.Consumer;
import java.util.function.Function;

public class BatchProcessor<T> {
    private final Function<Integer, Page<T>> fetchFunction;
    private final Consumer<Page<T>> processFunction;
    private static final Logger logger = Logger.getLogger(BatchProcessor.class);

    public BatchProcessor(Function<Integer, Page<T>> fetchFunction, Consumer<Page<T>> processFunction) {
        this.fetchFunction = fetchFunction;
        this.processFunction = processFunction;
    }

    public void execute() {
        int currentPage = 0;
        Page<T> batch;

        do {
            try {
                batch = fetchFunction.apply(currentPage);
                if (!batch.isEmpty()) {
                    processFunction.accept(batch);
                    currentPage++;
                } else {
                    break;
                }
            } catch (Exception e) {
                logger.error("An error occurred while processing batch at page {}. Error: {}", currentPage, e.getMessage());
                break;
            }
        } while (true);
    }
}
