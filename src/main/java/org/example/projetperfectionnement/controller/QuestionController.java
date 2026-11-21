package org.example.projetperfectionnement.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.projetperfectionnement.dto.QuestionCreateRequest;
import org.example.projetperfectionnement.entity.Question;
import org.example.projetperfectionnement.service.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping
    public ResponseEntity<Question> createQuestion(@Valid @RequestBody QuestionCreateRequest request) {
        Question question = questionService.createQuestion(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(question);
    }

    @GetMapping
    public ResponseEntity<List<Question>> getAllQuestions() {
        List<Question> questions = questionService.getAllQuestions();
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/niveau/{niveau}")
    public ResponseEntity<List<Question>> getQuestionsByNiveau(@PathVariable Question.DifficultyLevel niveau) {
        List<Question> questions = questionService.getQuestionsByNiveau(niveau);
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/interview/{interviewId}")
    public ResponseEntity<List<Question>> getQuestionsByInterviewId(@PathVariable Long interviewId) {
        List<Question> questions = questionService.getQuestionsByInterviewId(interviewId);
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable Long id) {
        Question question = questionService.getQuestionById(id);
        return ResponseEntity.ok(question);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable Long id, @Valid @RequestBody QuestionCreateRequest request) {
        Question question = questionService.updateQuestion(id, request);
        return ResponseEntity.ok(question);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }
}
