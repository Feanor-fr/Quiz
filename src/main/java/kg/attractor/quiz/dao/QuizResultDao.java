package kg.attractor.quiz.dao;

import kg.attractor.quiz.model.QuizResult;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class QuizResultDao {
    private final JdbcTemplate jdbcTemplate;

    public void save(QuizResult quizResult){
        String sql = """ 
                INSERT INTO QUIZ_RESULTS
                (USER_EMAIL, QUIZ_ID, SCORE)
                VALUES(?,?,?,?)
                """;

        jdbcTemplate.update(sql,
                quizResult.getUser(),
                quizResult.getQuizId(),
                quizResult.getScore()
        );
    }

    public void rate(String userEmail, Integer quizId, Integer rate) {
        String sql = """
            UPDATE quiz_results
               SET rate = ?
             WHERE user_email = ?
               AND quiz_id = ?
            """;

         jdbcTemplate.update(sql, rate, userEmail, quizId);
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

    public Map<String, Integer> getLeaderboardByQuizId(Integer quizId) {
        String sql = """
        SELECT USER_EMAIL , SCORE
        FROM QUIZ_RESULTS
        WHERE QUIZ_ID = ?
        ORDER BY SCORE DESC, USER_EMAIL ASC  
        """;
        return jdbcTemplate.query(sql, new Object[]{quizId}, rs -> {
            Map<String, Integer> map = new LinkedHashMap<>();
            while (rs.next()) {
                map.put(rs.getString("USER_EMAIL"), rs.getInt("SCORE"));
            }
            return map;
        });
    }

    public  Map<String, Integer> getUserStatistics(String userEmail) {
        String sql = """
        SELECT 
            COUNT(*) AS numberQuizzesCompleted,
            COALESCE(SUM(SCORE), 0) AS averageScore
        FROM QUIZ_RESULTS
        WHERE USER_EMAIL = ?
        """;

        return jdbcTemplate.query(sql, new Object[]{userEmail}, rs -> {
            Map<String, Integer> map = new HashMap<>();
            if (rs.next()) {
                map.put("numberQuizzesCompleted", rs.getInt("numberQuizzesCompleted"));
                map.put("averageScore", rs.getInt("averageScore"));
            }
            return map;
        });
    }
}
