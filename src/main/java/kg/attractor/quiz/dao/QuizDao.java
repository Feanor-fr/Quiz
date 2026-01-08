package kg.attractor.quiz.dao;

import kg.attractor.quiz.dto.view.QuizForViewDto;
import kg.attractor.quiz.model.Quiz;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

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

    public List<QuizForViewDto> findAllQuizzesWithQuestionCount(){
        String sql = """
                SELECT 
                q.TITLE,
                COUNT(qu.ID) AS questionCount
                FROM QUIZZES AS q
                INNER JOIN QUESTIONS qu ON qu.QUIZ_ID = q.ID
                GROUP BY q.ID, q.TITLE
                ORDER BY q.ID DESC
                """;

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(QuizForViewDto.class));
    }

    public Optional<Quiz> getQuizById(Integer id) {
        String sql = "select * from quizzes where id = ?";
        Quiz resume = DataAccessUtils.singleResult(
                jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Quiz.class), id)
        );
        return Optional.ofNullable(resume);
    }
}
