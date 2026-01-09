package kg.attractor.quiz.service;

import kg.attractor.quiz.dto.statistic.UserStatisticDto;
import kg.attractor.quiz.dto.statistic.UsersLeaderForOneQuizDto;
import kg.attractor.quiz.model.QuizResult;

import java.util.List;
import java.util.Optional;

public interface QuizResultService {
    void create(QuizResult quizResult);

    Optional<QuizResult> findByQuizIdAndUserEmail(String userEmail, Integer quizId);

    List<UsersLeaderForOneQuizDto> getLeaderboard(Integer quizId);

    UserStatisticDto getUserStatistics(String userEmail);

    void rate(String userEmail, Integer quizId, Integer rate);
}
