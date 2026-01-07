package kg.attractor.quiz.dto.game;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class QuizForGameDto {
    private Integer id;
    private String title;
    private List<QuestionForGameDto> questionForGameDtos;
}
