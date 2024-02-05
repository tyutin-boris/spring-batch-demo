package ru.boris.example.demo.tree.spring.batch.mapper;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Service;
import ru.boris.example.demo.tree.spring.batch.model.DialogueEntity;

@Service
public class DialogueEntityMapper implements FieldSetMapper<DialogueEntity> {

    @Override
    public DialogueEntity mapFieldSet(FieldSet fieldSet) {
        DialogueEntity entity = new DialogueEntity();

        entity.setIndex(fieldSet.readString(0));
        entity.setSeasonNo(fieldSet.readString(1));
        entity.setEpisodeNo(fieldSet.readString(2));
        entity.setEpisodeName(fieldSet.readString(3));
        entity.setName(fieldSet.readString(4));
        entity.setLine(fieldSet.readString(5));

        return entity;
    }
}
