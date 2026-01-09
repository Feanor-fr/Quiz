package kg.attractor.quiz.service.impl;

import kg.attractor.quiz.dao.QuizDao;
import kg.attractor.quiz.dto.create.QuestionForCreatedDto;
import kg.attractor.quiz.dto.create.QuizForCreatedDto;
import kg.attractor.quiz.dto.create.UserDto;
import kg.attractor.quiz.dto.game.QuizForGameDto;
import kg.attractor.quiz.dto.game.UserAnswerDto;
import kg.attractor.quiz.dto.statistic.RateDto;
import kg.attractor.quiz.dto.statistic.ResultDto;
import kg.attractor.quiz.dto.statistic.UsersLeaderForOneQuizDto;
import kg.attractor.quiz.dto.view.QuizDto;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
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
            log.info("questionDtos is null");
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
        List<Map<String, Object>> infos = quizDao.findAllQuizzesWithQuestionCount();
        log.info("infos {}", infos);

        return infos.stream()
                .map(info -> QuizForViewDto.builder()
                        .title((String) info.get("title"))
                        .questionCount((Integer) info.get("questionCount"))
                        .build())
                .toList();
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

        Optional<QuizResult> existingResult = quizResultService.findByQuizIdAndUserEmail(autUser.getEmail(), findQuiz.getId());

        if (existingResult.isPresent()) {
            throw new IncorrectInformationException("Вы уже проходили эту викторину!");
        }

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
                .userEmail(autUser.getEmail())
                .quizId(findQuiz.getId())
                .score(score)
                .build();

        quizResultService.create(quizResult);
    }

    @Override
    public ResultDto getUserResult(Integer quizId) {

        Quiz findQuiz = quizDao.getQuizById(quizId)
                .orElseThrow(QuizNotFoundException::new);

        String userEmail = userHelper.getAuthUser().getEmail();

        QuizResult userResult = quizResultService.findByQuizIdAndUserEmail(userEmail, findQuiz.getId())
                .orElseThrow(() -> new IncorrectInformationException(
                        "Вы ещё не проходили эту викторину!"));

        List<Question> questions = questionService.showAllByQuizId(quizId);

        List<String> correctAnswersTexts = new ArrayList<>();

        for (Question question : questions) {

            Option correctOption = optionService.showAllByQuestionId(question.getId()).stream()
                    .filter(o -> Boolean.TRUE.equals(o.getIsCorrect()))
                    .findFirst()
                    .orElseThrow(OptionNotFoundException::new);

            correctAnswersTexts.add(correctOption.getText());
        }

        return ResultDto.builder()
                .score(userResult.getScore())
                .optionsText(correctAnswersTexts)
                .build();
    }

    @Override
    public List<UsersLeaderForOneQuizDto> getLeaderboard(Integer quizId) {
        Quiz findQuiz = quizDao.getQuizById(quizId)
                .orElseThrow(QuizNotFoundException::new);

        return quizResultService.getLeaderboard(quizId);
    }

    @Override
    public void rateQuiz(Integer quizId, RateDto rateDto){
        Quiz findQuiz = quizDao.getQuizById(quizId)
                .orElseThrow(QuizNotFoundException::new);

        String userEmail = userHelper.getAuthUser().getEmail();

        QuizResult userResult = quizResultService.findByQuizIdAndUserEmail(userEmail, findQuiz.getId())
                .orElseThrow(() -> new IncorrectInformationException(
                        "Вы ещё не проходили эту викторину!"));

        if(userResult.getRate() != null){
            throw new  IncorrectInformationException(
                    "Оценить викторину можно только один раз!");
        }

        quizResultService.rate(userEmail, quizId, rateDto.getRate());
    }

    @Override
    public List<QuizDto> getQuizzesByCategory(Integer categoryId) {
        categoryService.findCategoryIdById(categoryId);

        List<Quiz> quizzes = quizDao.findByCategoryId(categoryId);

        return quizzes.stream()
                .map(ConvertHelper::convertToQuizDto)
                .toList();
    }
}
