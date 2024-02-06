package ru.boris.example.demo.tree.spring.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.boris.example.demo.tree.spring.batch.model.RickDialogueEntity;

@Repository
public interface RickDialogueRepository extends JpaRepository<RickDialogueEntity, Long> {
}
