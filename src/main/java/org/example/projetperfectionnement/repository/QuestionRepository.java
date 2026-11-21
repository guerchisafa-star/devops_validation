package org.example.projetperfectionnement.repository;

import org.example.projetperfectionnement.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByNiveau(Question.DifficultyLevel niveau);
    
    List<Question> findByInterviewId(Long interviewId);
    
    @Query("SELECT COUNT(q) FROM Question q WHERE q.interview.id = :interviewId")
    long countByInterviewId(@Param("interviewId") Long interviewId);
    
    List<Question> findByCategory(Question.QuestionCategory category);
    
    
    @Query("SELECT q FROM Question q WHERE q.interview.id = :interviewId AND q.niveau = :niveau")
    List<Question> findByInterviewIdAndNiveau(@Param("interviewId") Long interviewId, 
                                             @Param("niveau") Question.DifficultyLevel niveau);
}
