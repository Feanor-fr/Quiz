package kg.attractor.quiz.dao;

import kg.attractor.quiz.model.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;

@Component
@RequiredArgsConstructor
public class QuestionDao {
    private final JdbcTemplate jdbcTemplate;

    public Integer saveAndReturnId(Question question, Integer quizId) {
        String sql = """
        INSERT INTO QUESTIONS
        (QUIZ_ID, TEXT)
        VALUES (?, ?)
    """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setInt(1, quizId);
            ps.setString(2, question.getText());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    public List<Question> findAllByQuizId(Integer quizId){
        String sql = "SELECT * FROM QUESTIONS " +
                "where QUIZ_ID = ?";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Question.class), quizId);
    }

}
