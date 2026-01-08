package kg.attractor.quiz.util;

import kg.attractor.quiz.dto.create.OptionForCreatedDto;
import kg.attractor.quiz.dto.create.QuestionForCreatedDto;
import kg.attractor.quiz.dto.create.QuizForCreatedDto;
import kg.attractor.quiz.dto.create.UserDto;
import kg.attractor.quiz.model.Option;
import kg.attractor.quiz.model.Question;
import kg.attractor.quiz.model.Quiz;
import kg.attractor.quiz.model.User;

public class ConvertHelper {

    private ConvertHelper() {}

    public static User convertToUser(UserDto userDto){
        return User.builder()
                .userName(userDto.getUserName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .role(userDto.getRole())
                .build();
    }

    public static UserDto convertToUserDto(User user) {
        return UserDto.builder()
                .userName(user.getUserName())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }

    public static Option convertToOption(OptionForCreatedDto option){
        return Option.builder()
                .text(option.getText())
                .isCorrect(option.getIsCorrect())
                .build();
    }

    public static Question convertToQuestion(QuestionForCreatedDto question){
        return Question.builder()
                .text(question.getText())
                .build();
    }

    public static Quiz convertToQuiz(QuizForCreatedDto quiz){
        return Quiz.builder()
                .creator(quiz.getCreator())
                .title(quiz.getTitle())
                .description(quiz.getDescription())
                .categoryId(quiz.getCategoryId())
                .build();
    }
}
