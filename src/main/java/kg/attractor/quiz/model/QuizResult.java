package kg.attractor.quiz.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuizResult {
    private Integer id;
    private String user;
    private Integer quizId;
    private Integer score;
    private Integer rate;
}
