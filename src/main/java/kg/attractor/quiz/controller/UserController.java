package kg.attractor.quiz.controller;

import kg.attractor.quiz.dto.create.UserDto;
import kg.attractor.quiz.dto.statistic.UserStatisticDto;
import kg.attractor.quiz.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

      @PostMapping("/api/register")
    public ResponseEntity<Void> register(@RequestBody UserDto userDto){
          userService.create(userDto);
          return ResponseEntity.status(HttpStatus.CREATED).build();
      }

      @GetMapping("/api/users/{email}/statistics")
    public ResponseEntity<UserStatisticDto> showUserStatistic(@PathVariable String email){
          return ResponseEntity.ok(new UserStatisticDto());
      }
}
