package ru.boris.example.demo.tree.spring.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.stereotype.Service;
import ru.boris.example.demo.tree.spring.batch.model.DialogueEntity;

@Slf4j
@Service
public class DialogReadListener implements ItemReadListener<DialogueEntity> {

    @Override
    public void beforeRead() {
        log.info("ReadListener before");
    }

    @Override
    public void afterRead(DialogueEntity item) {
        log.info("ReadListener afterRead item: {}", item);
    }

    @Override
    public void onReadError(Exception ex) {
        log.error("ReaderListener onReadError", ex);
    }
}
