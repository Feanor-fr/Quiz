package kg.attractor.quiz.dao;

import kg.attractor.quiz.model.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OptionDao {
    private final JdbcTemplate jdbcTemplate;

    public void save(Option option, Integer questionId){
        String sql = """ 
                INSERT INTO OPTIONS
                (QUESTION_ID, TEXT, IS_CORRECT)
                VALUES(?,?,?)
                """;

        jdbcTemplate.update(sql,
                questionId,
                option.getText(),
                option.getIsCorrect()
        );
    }

    public List<Option> findAllByQuestionId(Integer questionId){
        String sql = "SELECT * FROM OPTIONS " +
                "where QUESTION_ID = ?";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Option.class), questionId);
    }
}
