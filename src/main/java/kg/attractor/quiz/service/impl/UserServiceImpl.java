package kg.attractor.quiz.service.impl;

import kg.attractor.quiz.dao.UserDao;
import kg.attractor.quiz.dto.create.UserDto;
import kg.attractor.quiz.exception.UserAlreadyExistsException;
import kg.attractor.quiz.model.User;
import kg.attractor.quiz.service.UserService;
import kg.attractor.quiz.util.ConvertHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void create(UserDto userDto) {

        User user = ConvertHelper.convertToUser(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        List<User> users = userDao.getAllUsers();

        for (User u : users) {
            if (u.getEmail().equalsIgnoreCase(user.getEmail())) {
                throw new UserAlreadyExistsException("Пользователь с таким email уже существует!");
            }
        }

        userDao.save(user);
    }


}
