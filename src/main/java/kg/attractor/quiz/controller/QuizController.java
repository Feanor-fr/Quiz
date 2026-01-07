package kg.attractor.quiz.controller;

import kg.attractor.quiz.dto.create.QuizForCreatedDto;
import kg.attractor.quiz.dto.game.QuizForGameDto;
import kg.attractor.quiz.dto.game.UserAnswerDto;
import kg.attractor.quiz.dto.statistic.RateDto;
import kg.attractor.quiz.dto.statistic.ResultDto;
import kg.attractor.quiz.dto.statistic.UsersLeaderForOneQuizDto;
import kg.attractor.quiz.dto.view.QuizForViewDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quizzes")
public class QuizController {

    @PostMapping("/api/quizzes")
    public ResponseEntity<Void> create(@RequestBody QuizForCreatedDto quizForCreatedDto){
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/api/quizzes")
    public ResponseEntity<List<QuizForViewDto>> allQuizzesToView(){
        return ResponseEntity.ok(List.of());
    }

    @GetMapping("/api/quizzes/{quizId}")
    public ResponseEntity<QuizForGameDto> showQuizForGame(@PathVariable Integer quizId){
        return ResponseEntity.ok(new QuizForGameDto());
    }

    @PostMapping("/api/quizzes/{quizId}/slove")
    public ResponseEntity<Void> answerQuizQuestions(@PathVariable Integer quizId, @RequestBody UserAnswerDto userAnswerDto){
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/api/quizzes/{quizId}/results")
    public ResponseEntity<ResultDto> showUserResultForOneQuiz(@PathVariable Integer quizId){
        return ResponseEntity.ok(new ResultDto());
    }

    @PostMapping("/api/quizzes/{quizId}/rate")
    public ResponseEntity<Void> rateQuiz(@PathVariable Integer quizId, @RequestBody RateDto rateDto){
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/api/quizzes/{quizId}/leaderboard")
    public ResponseEntity<List<UsersLeaderForOneQuizDto>> leadersForOneQuiz(@PathVariable Integer quizId) {
        return ResponseEntity.ok(List.of());
    }

}
