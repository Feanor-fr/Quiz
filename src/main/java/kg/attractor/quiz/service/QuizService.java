package kg.attractor.quiz.service;

import kg.attractor.quiz.dto.create.QuizForCreatedDto;
import kg.attractor.quiz.dto.game.QuizForGameDto;
import kg.attractor.quiz.dto.view.QuizForViewDto;

import java.util.List;

public interface QuizService {
    void create(QuizForCreatedDto quizDto);

    List<QuizForViewDto> findAllQuizzesWithQuestionCount();

    QuizForGameDto showOneQuizForGame(Integer quizId);
}
