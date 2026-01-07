package kg.attractor.quiz.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Question {
    private Integer id;
    private Integer quizId;
    private String text;
}
