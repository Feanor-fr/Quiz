package kg.attractor.quiz.dto.create;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "Поле c вопросом не должно быть пустым!")
    private String text;

    @Valid
    private List<OptionForCreatedDto> optionDtos;
}
