package kg.attractor.quiz.dto.game;

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
    private Map<Integer, Integer> answers;
    private Integer rate;
}
