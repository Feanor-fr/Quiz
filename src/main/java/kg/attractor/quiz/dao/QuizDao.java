package kg.attractor.quiz.dao;

import kg.attractor.quiz.model.Quiz;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;

@Component
@RequiredArgsConstructor
public class QuizDao {
    private final JdbcTemplate jdbcTemplate;

    public Integer saveAndReturnId(Quiz quiz) {
        String sql = """
        INSERT INTO QUIZZES
        (CREATOR, TITLE, DESCRIPTION, CATEGORY_ID)
        VALUES (?, ?, ?, ?)
    """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, quiz.getCreator());
            ps.setString(2, quiz.getTitle());
            ps.setString(3, quiz.getDescription());
            ps.setInt(4, quiz.getCategoryId() );
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }
}
