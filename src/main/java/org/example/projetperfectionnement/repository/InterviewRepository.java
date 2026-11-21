package org.example.projetperfectionnement.repository;

import org.example.projetperfectionnement.entity.Interview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface InterviewRepository extends JpaRepository<Interview, Long> {
    List<Interview> findByPoste(String poste);
    long countByDateCreationBetween(LocalDateTime start, LocalDateTime end);
}
