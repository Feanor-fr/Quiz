package kg.attractor.quiz.dao;

import kg.attractor.quiz.dto.statistic.UserStatisticDto;
import kg.attractor.quiz.dto.statistic.UsersLeaderForOneQuizDto;
import kg.attractor.quiz.model.QuizResult;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class QuizResultDao {
    private final JdbcTemplate jdbcTemplate;

    public void save(QuizResult quizResult){
        String sql = """ 
                INSERT INTO QUIZ_RESULTS
                (USER_EMAIL, QUIZ_ID, SCORE, RATE)
                VALUES(?,?,?,?)
                """;

        jdbcTemplate.update(sql,
                quizResult.getUser(),
                quizResult.getQuizId(),
                quizResult.getScore(),
                quizResult.getRate()
        );
    }

    public Optional<QuizResult> findByQuizIdAndUserEmail(String userEmail, Integer quizId) {
        String sql = """
            SELECT * FROM QUIZ_RESULTS
            WHERE USER_EMAIL = ? AND QUIZ_ID = ?
            """;
        QuizResult quizResult = DataAccessUtils.singleResult(
                jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(QuizResult.class), userEmail, quizId)
        );

        return Optional.ofNullable(quizResult);
    }

    public List<UsersLeaderForOneQuizDto> getLeaderboardByQuizId(Integer quizId) {
        String sql = """
        SELECT USER_EMAIL AS email, SCORE
        FROM QUIZ_RESULTS
        WHERE QUIZ_ID = ?
        ORDER BY SCORE DESC, USER_EMAIL ASC  
        """;

        return jdbcTemplate.query(sql,
                new BeanPropertyRowMapper<>(UsersLeaderForOneQuizDto.class),
                quizId);
    }

    public Optional<UserStatisticDto> getUserStatistics(String userEmail) {
        String sql = """
        SELECT 
            COUNT(*) AS numberQuizzesCompleted,
            COALESCE(SUM(SCORE), 0) AS averageScore
        FROM QUIZ_RESULTS
        WHERE USER_EMAIL = ?
        """;

        UserStatisticDto userStatistic = DataAccessUtils.singleResult(
                jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(UserStatisticDto.class), userEmail)
        );

        return Optional.ofNullable(userStatistic);
    }
}
