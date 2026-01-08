package kg.attractor.quiz.service;

import kg.attractor.quiz.dto.create.UserDto;

public interface UserService {
    void create(UserDto userDto);

    UserDto getUserByEmail(String email);
}
