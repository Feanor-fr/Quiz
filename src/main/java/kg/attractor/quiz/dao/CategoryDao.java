package kg.attractor.quiz.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CategoryDao {
    private final JdbcTemplate jdbcTemplate;

    public Optional<Integer> findCategoryIdById(Integer id){
        String sql = "SELECT ID FROM CATEGORIES " +
                "where ID = ?";

        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(sql, Integer.class, id)
            );
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
