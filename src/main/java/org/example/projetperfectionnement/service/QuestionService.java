package org.example.projetperfectionnement.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.projetperfectionnement.dto.QuestionCreateRequest;
import org.example.projetperfectionnement.entity.Interview;
import org.example.projetperfectionnement.entity.Question;
import org.example.projetperfectionnement.exception.BusinessException;
import org.example.projetperfectionnement.exception.ResourceNotFoundException;
import org.example.projetperfectionnement.repository.InterviewRepository;
import org.example.projetperfectionnement.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final InterviewRepository interviewRepository;

    public Question createQuestion(QuestionCreateRequest request) {
        log.info("Création d'une nouvelle question pour l'entretien ID: {}", request.getInterviewId());
        
        Interview interview = interviewRepository.findById(request.getInterviewId())
                .orElseThrow(() -> new ResourceNotFoundException("Entretien non trouvé avec l'ID: " + request.getInterviewId()));
        
        // Vérifier si on n'a pas dépassé le nombre de questions autorisées
        long currentQuestionsCount = questionRepository.countByInterviewId(request.getInterviewId());
        if (currentQuestionsCount >= interview.getNombreQuestions()) {
            throw new BusinessException("Le nombre maximum de questions (" + interview.getNombreQuestions() + ") est atteint pour cet entretien");
        }
        
        Question question = new Question();
        question.setQuestion(request.getQuestion());
        question.setChoixA(request.getChoixA());
        question.setChoixB(request.getChoixB());
        question.setChoixC(request.getChoixC());
        question.setChoixD(request.getChoixD());
        question.setBonneReponse(request.getBonneReponse());
        question.setNiveau(request.getNiveau());
        question.setCategory(request.getCategory());
        question.setInterview(interview);
        
        Question savedQuestion = questionRepository.save(question);
        
        // Mettre à jour le statut de l'entretien si toutes les questions sont créées
        updateInterviewStatusIfComplete(interview);
        
        log.info("Question créée avec l'ID: {}", savedQuestion.getId());
        return savedQuestion;
    }

    public List<Question> getAllQuestions() {
        log.info("Récupération de toutes les questions");
        return questionRepository.findAll();
    }

    public List<Question> getQuestionsByNiveau(Question.DifficultyLevel niveau) {
        log.info("Récupération des questions de niveau: {}", niveau);
        return questionRepository.findByNiveau(niveau);
    }

    public List<Question> getQuestionsByInterviewId(Long interviewId) {
        log.info("Récupération des questions pour l'entretien ID: {}", interviewId);
        return questionRepository.findByInterviewId(interviewId);
    }

    public Question getQuestionById(Long id) {
        log.info("Récupération de la question avec l'ID: {}", id);
        return questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question non trouvée avec l'ID: " + id));
    }

    public Question updateQuestion(Long id, QuestionCreateRequest request) {
        log.info("Mise à jour de la question avec l'ID: {}", id);
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question non trouvée avec l'ID: " + id));
        
        question.setQuestion(request.getQuestion());
        question.setChoixA(request.getChoixA());
        question.setChoixB(request.getChoixB());
        question.setChoixC(request.getChoixC());
        question.setChoixD(request.getChoixD());
        question.setBonneReponse(request.getBonneReponse());
        question.setNiveau(request.getNiveau());
        question.setCategory(request.getCategory());
        
        return questionRepository.save(question);
    }

    public void deleteQuestion(Long id) {
        log.info("Suppression de la question avec l'ID: {}", id);
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question non trouvée avec l'ID: " + id));
        
        questionRepository.deleteById(id);
        
        // Mettre à jour le statut de l'entretien
        if (question.getInterview() != null) {
            updateInterviewStatusIfComplete(question.getInterview());
        }
    }

    private void updateInterviewStatusIfComplete(Interview interview) {
        long questionsCount = questionRepository.countByInterviewId(interview.getId());
        
        if (questionsCount >= interview.getNombreQuestions() && 
            interview.getStatus() == Interview.InterviewStatus.BROUILLON) {
            interview.setStatus(Interview.InterviewStatus.PRET);
            interviewRepository.save(interview);
            log.info("Entretien ID {} marqué comme PRET", interview.getId());
        } else if (questionsCount < interview.getNombreQuestions() && 
                   interview.getStatus() == Interview.InterviewStatus.PRET) {
            interview.setStatus(Interview.InterviewStatus.BROUILLON);
            interviewRepository.save(interview);
            log.info("Entretien ID {} marqué comme BROUILLON", interview.getId());
        }
    }
}
