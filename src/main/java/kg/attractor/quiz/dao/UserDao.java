package kg.attractor.quiz.dao;

import kg.attractor.quiz.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserDao {
    private final JdbcTemplate jdbcTemplate;

    public void save(User user) {
        String sql = """
            INSERT INTO users
            (user_name, email, password, role)
            VALUES (?, ?, ?, ?)
        """;

        jdbcTemplate.update(
                sql,
                user.getUserName(),
                user.getEmail(),
                user.getPassword(),
                user.getRole()
        );
    }

    public List<User> getAllUsers(){
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
    }

    public Optional<User> findUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";

        User user = DataAccessUtils.singleResult(jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(User.class),
                email
        ));

        return Optional.ofNullable(user);
    }
}
