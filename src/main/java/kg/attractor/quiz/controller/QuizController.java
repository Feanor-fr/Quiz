package kg.attractor.quiz.controller;

import jakarta.validation.Valid;
import kg.attractor.quiz.dto.create.QuizForCreatedDto;
import kg.attractor.quiz.dto.game.QuizForGameDto;
import kg.attractor.quiz.dto.game.UserAnswerDto;
import kg.attractor.quiz.dto.statistic.RateDto;
import kg.attractor.quiz.dto.statistic.ResultDto;
import kg.attractor.quiz.dto.statistic.UsersLeaderForOneQuizDto;
import kg.attractor.quiz.dto.view.QuizForViewDto;
import kg.attractor.quiz.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quizzes")
@RequiredArgsConstructor
public class QuizController {
    private final QuizService quizService;

    @PostMapping("/api/quizzes")
    public ResponseEntity<Void> create(@Valid @RequestBody QuizForCreatedDto quizForCreatedDto){
        quizService.create(quizForCreatedDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/api/quizzes")
    public ResponseEntity<List<QuizForViewDto>> allQuizzesToView(){
        return ResponseEntity.ok(quizService.findAllQuizzesWithQuestionCount());
    }

    @GetMapping("/api/quizzes/{quizId}")
    public ResponseEntity<QuizForGameDto> showQuizForGame(@PathVariable Integer quizId){
        return ResponseEntity.ok(quizService.showOneQuizForGame(quizId));
    }

    @PostMapping("/api/quizzes/{quizId}/slove")
    public ResponseEntity<Void> answerQuizQuestions(@PathVariable Integer quizId, @Valid @RequestBody UserAnswerDto userAnswerDto){
        quizService.userAnswers(userAnswerDto, quizId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/api/quizzes/{quizId}/results")
    public ResponseEntity<ResultDto> showUserResultForOneQuiz(@PathVariable Integer quizId){
        return ResponseEntity.ok(quizService.getUserResult(quizId));
    }

    @PostMapping("/api/quizzes/{quizId}/rate")
    public ResponseEntity<Void> rateQuiz(@PathVariable Integer quizId, @RequestBody RateDto rateDto){
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/api/quizzes/{quizId}/leaderboard")
    public ResponseEntity<List<UsersLeaderForOneQuizDto>> leadersForOneQuiz(@PathVariable Integer quizId) {
        return ResponseEntity.ok(quizService.getLeaderboard(quizId));
    }

}
