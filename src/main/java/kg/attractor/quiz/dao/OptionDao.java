package kg.attractor.quiz.dao;

import kg.attractor.quiz.model.Option;
import kg.attractor.quiz.model.Quiz;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

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

    public Optional<Option> getOptionById(Integer id) {
        String sql = "select * from OPTIONS  where id = ?";
        Option option = DataAccessUtils.singleResult(
                jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Option.class), id)
        );
        return Optional.ofNullable(option);
    }
}
