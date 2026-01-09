package kg.attractor.quiz.service.impl;

import kg.attractor.quiz.dao.QuizResultDao;
import kg.attractor.quiz.dto.statistic.UserStatisticDto;
import kg.attractor.quiz.dto.statistic.UsersLeaderForOneQuizDto;
import kg.attractor.quiz.model.QuizResult;
import kg.attractor.quiz.service.QuizResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class QuizResultServiceImpl implements QuizResultService {
    private final QuizResultDao quizResultDao;

    @Override
    public void create(QuizResult quizResult){
        quizResultDao.save(quizResult);
    }

    @Override
    public Optional<QuizResult> findByQuizIdAndUserEmail(String userEmail, Integer quizId){
        return quizResultDao.findByQuizIdAndUserEmail(userEmail, quizId);
    }

    @Override
    public List<UsersLeaderForOneQuizDto> getLeaderboard(Integer quizId) {
        Map<String, Integer> scoresMap = quizResultDao.getLeaderboardByQuizId(quizId);

        return scoresMap.entrySet().stream()
                .map(entry -> UsersLeaderForOneQuizDto.builder()
                        .email( entry.getKey())
                        .score( entry.getValue())
                        .build())
                .toList();
    }


    @Override
    public UserStatisticDto getUserStatistics(String userEmail) {
        Map<String, Integer> statMap = quizResultDao.getUserStatistics(userEmail);

        int completed = statMap.getOrDefault("numberQuizzesCompleted", 0);
        int sum = statMap.getOrDefault("averageScore", 0);
        int average = completed == 0 ? 0 : sum / completed;

        return UserStatisticDto.builder()
                .numberQuizzesCompleted(completed)
                .averageScore(average)
                .build();
    }

    @Override
    public void rate(String userEmail, Integer quizId, Integer rate) {
        quizResultDao.rate(userEmail, quizId, rate);
    }


}
