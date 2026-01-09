package kg.attractor.quiz.dto.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizDto {
    private Integer id;
    private String creator;
    private String title;
    private String description;
    private Integer categoryId;
}
