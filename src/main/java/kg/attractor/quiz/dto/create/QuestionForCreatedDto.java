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
public class QuestionForCreatedDto {
    private Integer quizId;
    private String text;
    private List<OptionForCreatedDto> optionDtos;
}
