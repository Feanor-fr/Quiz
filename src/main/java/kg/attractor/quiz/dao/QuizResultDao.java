package kg.attractor.quiz.dao;

import kg.attractor.quiz.model.QuizResult;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

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
}
