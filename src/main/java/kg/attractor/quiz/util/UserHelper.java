package kg.attractor.quiz.util;

import kg.attractor.quiz.dto.create.UserDto;
import kg.attractor.quiz.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserHelper {
    private final UserService userService;

    public UserDto getAuthUser(){
        return userService
                .getUserByEmail(SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName());
    }

}
