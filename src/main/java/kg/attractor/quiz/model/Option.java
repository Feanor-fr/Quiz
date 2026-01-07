package kg.attractor.quiz.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Option {
    private Integer id;
    private Integer questionId;
    private String text;
    private Boolean isCorrect;
}
