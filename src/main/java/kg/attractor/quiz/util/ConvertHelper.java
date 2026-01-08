package kg.attractor.quiz.util;

import kg.attractor.quiz.dto.create.UserDto;
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
}
