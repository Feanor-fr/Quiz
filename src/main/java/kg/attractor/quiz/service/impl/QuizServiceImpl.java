package kg.attractor.quiz.service.impl;

import kg.attractor.quiz.dao.QuizDao;
import kg.attractor.quiz.dto.create.QuestionForCreatedDto;
import kg.attractor.quiz.dto.create.QuizForCreatedDto;
import kg.attractor.quiz.dto.create.UserDto;
import kg.attractor.quiz.exception.IncorrectInformationException;
import kg.attractor.quiz.model.Quiz;
import kg.attractor.quiz.service.CategoryService;
import kg.attractor.quiz.service.QuestionService;
import kg.attractor.quiz.service.QuizService;
import kg.attractor.quiz.service.UserService;
import kg.attractor.quiz.util.ConvertHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {
    private final QuizDao quizDao;
    private final CategoryService categoryService;
    private final UserService userService;
    private final QuestionService questionService;

    @Transactional
    @Override
    public void create(QuizForCreatedDto quizDto){
        UserDto userDto = userService.getUserByEmail(quizDto.getCreator());
        quizDto.setCategoryId(categoryService.findCategoryIdById(quizDto.getCategoryId()));

        List<QuestionForCreatedDto> questionDtos = quizDto.getQuestionDtos();

        if (questionDtos == null || questionDtos.isEmpty()) {
            throw new IncorrectInformationException("Вопросы отсутствуют!");
        }

        Quiz quiz = ConvertHelper.convertToQuiz(quizDto);
        Integer quizId = quizDao.saveAndReturnId(quiz);

        for(QuestionForCreatedDto q : questionDtos) {
            questionService.create(q, quizId);
        }

    }
}
