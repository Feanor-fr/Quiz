package kg.attractor.quiz.dto.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class OptionForCreatedDto {
    private Integer questionId;
    private String text;
    private Boolean isCorrect;
}
