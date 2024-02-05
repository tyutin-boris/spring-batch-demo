package ru.boris.example.demo.tree.spring.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.item.Chunk;
import org.springframework.stereotype.Service;
import ru.boris.example.demo.tree.spring.batch.model.DialogueEntity;

@Slf4j
@Service
public class DialogWriteListener implements ItemWriteListener<DialogueEntity> {

    @Override
    public void beforeWrite(Chunk<? extends DialogueEntity> items) {
        log.info("WriteListener beforeWrite: {}", items);
    }

    @Override
    public void afterWrite(Chunk<? extends DialogueEntity> items) {
        log.info("WriteListener afterWrite: {}", items);
    }

    @Override
    public void onWriteError(Exception exception, Chunk<? extends DialogueEntity> items) {
        log.error("WriteListener onWriteError: {}", items);
    }
}
