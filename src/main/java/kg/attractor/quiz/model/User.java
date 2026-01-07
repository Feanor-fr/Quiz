package kg.attractor.quiz.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private String userName;
    private String email;
    private String password;
    private Integer role;
}
