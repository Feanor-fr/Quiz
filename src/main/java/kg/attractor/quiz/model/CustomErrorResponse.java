package kg.attractor.quiz.model;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomErrorResponse {
    private String error;
    private Map<String, List<String>> reasons;
}
