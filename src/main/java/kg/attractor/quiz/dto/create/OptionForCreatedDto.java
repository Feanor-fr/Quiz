package kg.attractor.quiz.dto.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class OptionForCreatedDto {

    @NotBlank(message = "Поле c ответом не должно быть пустым!")
    private String text;

    @NotNull(message = "Допустимые значения для поля isCorrect: true или false!")
    private Boolean isCorrect;
}
