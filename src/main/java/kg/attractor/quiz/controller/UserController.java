package kg.attractor.quiz.controller;

import kg.attractor.quiz.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

      @PostMapping("/api/register")
    public ResponseEntity<Void> register(@RequestBody UserDto userDto){
          return ResponseEntity.status(HttpStatus.CREATED).build();
      }
}
