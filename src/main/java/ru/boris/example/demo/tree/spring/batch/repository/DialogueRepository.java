package ru.boris.example.demo.tree.spring.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.boris.example.demo.tree.spring.batch.model.DialogueEntity;

@Repository
public interface DialogueRepository extends JpaRepository<DialogueEntity, Long> {
}
