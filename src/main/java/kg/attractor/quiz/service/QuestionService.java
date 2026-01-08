package kg.attractor.quiz.service;

import kg.attractor.quiz.dto.create.QuestionForCreatedDto;
import kg.attractor.quiz.dto.game.QuestionForGameDto;

import java.util.List;

public interface QuestionService {
    void create(QuestionForCreatedDto questionDto, Integer quizId);

    List<QuestionForGameDto> showQuizQuestionForGame(Integer quizId);
}
