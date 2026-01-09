package kg.attractor.quiz.service;

import kg.attractor.quiz.dto.create.QuizForCreatedDto;
import kg.attractor.quiz.dto.game.QuizForGameDto;
import kg.attractor.quiz.dto.game.UserAnswerDto;
import kg.attractor.quiz.dto.statistic.RateDto;
import kg.attractor.quiz.dto.statistic.ResultDto;
import kg.attractor.quiz.dto.statistic.UsersLeaderForOneQuizDto;
import kg.attractor.quiz.dto.view.QuizForViewDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface QuizService {
    void create(QuizForCreatedDto quizDto);

    List<QuizForViewDto> findAllQuizzesWithQuestionCount();

    QuizForGameDto showOneQuizForGame(Integer quizId);

    @Transactional
    void userAnswers(UserAnswerDto userAnswerDto, Integer quizId);

    ResultDto getUserResult(Integer quizId);

    List<UsersLeaderForOneQuizDto> getLeaderboard(Integer quizId);

    void rateQuiz(Integer quizId, RateDto rateDto);
}
