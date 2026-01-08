package kg.attractor.quiz.service.impl;

import kg.attractor.quiz.dao.QuizDao;
import kg.attractor.quiz.dto.create.QuestionForCreatedDto;
import kg.attractor.quiz.dto.create.QuizForCreatedDto;
import kg.attractor.quiz.dto.create.UserDto;
import kg.attractor.quiz.dto.game.QuizForGameDto;
import kg.attractor.quiz.dto.game.UserAnswerDto;
import kg.attractor.quiz.dto.view.QuizForViewDto;
import kg.attractor.quiz.exception.IncorrectInformationException;
import kg.attractor.quiz.exception.OptionNotFoundException;
import kg.attractor.quiz.exception.QuizNotFoundException;
import kg.attractor.quiz.model.Option;
import kg.attractor.quiz.model.Question;
import kg.attractor.quiz.model.Quiz;
import kg.attractor.quiz.model.QuizResult;
import kg.attractor.quiz.service.*;
import kg.attractor.quiz.util.ConvertHelper;
import kg.attractor.quiz.util.UserHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {
    private final QuizDao quizDao;
    private final CategoryService categoryService;
    private final UserService userService;
    private final QuestionService questionService;
    private final UserHelper userHelper;
    private final OptionService optionService;
    private final QuizResultService quizResultService;

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

    @Override
    public List<QuizForViewDto> findAllQuizzesWithQuestionCount(){
        return quizDao.findAllQuizzesWithQuestionCount();
    }

    @Override
    public QuizForGameDto showOneQuizForGame(Integer quizId){
        Quiz findQuiz = quizDao.getQuizById(quizId).orElseThrow(QuizNotFoundException::new);

        QuizForGameDto quizGame = ConvertHelper.convertToQuizForGameDto(findQuiz);

        quizGame.setQuestionForGameDtos(questionService.showQuizQuestionForGame(quizGame.getId()));

        return quizGame;
    }

    @Transactional
    @Override
    public void userAnswers(UserAnswerDto userAnswerDto, Integer quizId){
        Quiz findQuiz = quizDao.getQuizById(quizId).orElseThrow(QuizNotFoundException::new);

        UserDto autUser = userHelper.getAuthUser();

        Map<Integer, Integer> answers = userAnswerDto.getAnswers();

        for (Map.Entry<Integer, Integer> entry : answers.entrySet()) {
            Integer questionId = entry.getKey();
            Integer optionId = entry.getValue();

            if (questionId == null || questionId <= 0) {
                throw new IncorrectInformationException("ID вопроса должен быть положительным числом!");
            }

            if (optionId == null || optionId <= 0) {
                throw new IncorrectInformationException("ID выбранного ответа должен быть положительным числом!");
            }
        }

        List<Question> questions = questionService.showAllByQuizId(findQuiz.getId());

        Set<Integer> showQuestionIds = questions.stream()
                .map(Question::getId)
                .collect(Collectors.toSet());

        for (Integer questionId : answers.keySet()) {
            if (!showQuestionIds.contains(questionId)) {
                throw new IncorrectInformationException("ID вопроса " + questionId + " не принадлежит этой викторине!");
            }
        }

        Integer score = 0;

        for (Map.Entry<Integer, Integer> entry : answers.entrySet()) {
            Integer questionId = entry.getKey();
            Integer optionId = entry.getValue();

              Option option = optionService.getOptionById(optionId).orElseThrow(OptionNotFoundException::new);

            if (!option.getQuestionId().equals(questionId)) {
                throw new IncorrectInformationException("ID ответа " + optionId + " не принадлежит вопросу " + questionId + "!");
            }

            if (Boolean.TRUE.equals(option.getIsCorrect())) {
                score++;
            }
        }

        QuizResult quizResult = QuizResult.builder()
                .user(autUser.getEmail())
                .quizId(findQuiz.getId())
                .score(score)
                .rate(userAnswerDto.getRate())
                .build();

        quizResultService.create(quizResult);
    }
}
