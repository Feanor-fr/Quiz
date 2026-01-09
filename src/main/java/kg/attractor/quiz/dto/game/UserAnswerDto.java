package kg.attractor.quiz.dto.game;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserAnswerDto {

    @NotNull(message = "Ответы обязательны")
    @Size(min = 1, message = "Вы не ответили на вопросы!")
    private Map<Integer, Integer> answers;
}
