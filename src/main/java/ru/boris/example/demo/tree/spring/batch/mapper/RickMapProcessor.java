package ru.boris.example.demo.tree.spring.batch.mapper;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;
import ru.boris.example.demo.tree.spring.batch.model.DialogueEntity;
import ru.boris.example.demo.tree.spring.batch.model.RickDialogueEntity;

@Service
public class RickMapProcessor implements ItemProcessor<DialogueEntity, RickDialogueEntity> {

    @Override
    public RickDialogueEntity process(DialogueEntity item) {
        if(!"Rick".equals(item.getName())) {
            return null;
        }

        RickDialogueEntity entity = new RickDialogueEntity();

        entity.setIndex(item.getIndex());
        entity.setSeasonNo(item.getSeasonNo());
        entity.setEpisodeNo(item.getEpisodeNo());
        entity.setEpisodeName(item.getEpisodeName());
        entity.setName(item.getName());
        entity.setLine(item.getLine());

        return entity;
    }
}
