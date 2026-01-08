package kg.attractor.quiz.dto.create;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
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

    @NotBlank(message = "Поле создатель не может быть пустым!")
    @Email(message = "Поле должно быть указанно в формате qwe@.qwe")
    private String creator;

    @NotBlank(message = "Поле название не должно быть пустым!")
    private String title;

    @NotBlank(message = "Поле описание не должно быть пустым!")
    private String description;

    @NotNull(message = "Поле категории не может быть пустым!")
    private Integer categoryId;


    @Valid
    private List<QuestionForCreatedDto> questionDtos;
}
