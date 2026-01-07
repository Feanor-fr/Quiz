package kg.attractor.quiz.dto.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class QuizForViewDto {
    private String title;
    private Integer questionCount;
}
