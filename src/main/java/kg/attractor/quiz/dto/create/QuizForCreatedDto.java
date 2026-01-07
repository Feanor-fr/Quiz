package kg.attractor.quiz.dto.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class QuizForCreatedDto {
    private String creator;
    private String title;
    private String description;
    private Integer categoryId;
    private List<QuestionForCreatedDto> questionDtos;
}
