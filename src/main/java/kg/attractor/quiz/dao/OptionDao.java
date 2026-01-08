package kg.attractor.quiz.dao;

import kg.attractor.quiz.model.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

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
}
