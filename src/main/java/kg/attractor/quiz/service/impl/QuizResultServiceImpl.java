package kg.attractor.quiz.service.impl;

import kg.attractor.quiz.dao.QuizResultDao;
import kg.attractor.quiz.dto.statistic.UserStatisticDto;
import kg.attractor.quiz.dto.statistic.UsersLeaderForOneQuizDto;
import kg.attractor.quiz.model.QuizResult;
import kg.attractor.quiz.service.QuizResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        return quizResultDao.getLeaderboardByQuizId(quizId);
    }


    @Override
    public UserStatisticDto getUserStatistics(String userEmail) {
        return quizResultDao.getUserStatistics(userEmail)
                .map(stat -> {
                    int completed = stat.getNumberQuizzesCompleted();
                    int sum = stat.getAverageScore();
                    int average = completed == 0 ? 0 : sum / completed;
                    stat.setAverageScore(average);
                    return stat;
                })
                .orElse(UserStatisticDto.builder()
                        .numberQuizzesCompleted(0)
                        .averageScore(0)
                        .build());
    }


}
