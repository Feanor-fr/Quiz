package kg.attractor.quiz.service;

import kg.attractor.quiz.dto.create.QuestionForCreatedDto;

public interface QuestionService {
    void create(QuestionForCreatedDto questionDto, Integer quizId);
}
