package ru.boris.example.demo.tree.spring.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DialogChunkListener implements ChunkListener {

    @Override
    public void beforeChunk(ChunkContext context) {
        log.info("ChunkListener before context: {}", context);
    }

    @Override
    public void afterChunk(ChunkContext context) {
        log.info("ChunkListener after context: {}", context);
    }

    @Override
    public void afterChunkError(ChunkContext context) {
        log.error("ChunkListener afterChunkError context: {}", context);
    }
}
