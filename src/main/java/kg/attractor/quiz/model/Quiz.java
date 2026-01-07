package kg.attractor.quiz.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Quiz {
    private Integer id;
    private String creator;
    private String title;
    private String description;
    private Integer categoryId;
}
