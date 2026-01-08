package kg.attractor.quiz.service;

import kg.attractor.quiz.dto.create.QuizForCreatedDto;

public interface QuizService {
    void create(QuizForCreatedDto quizDto);
}
