package kg.attractor.quiz.service.impl;

import kg.attractor.quiz.dao.QuestionDao;
import kg.attractor.quiz.dto.create.OptionForCreatedDto;
import kg.attractor.quiz.dto.create.QuestionForCreatedDto;
import kg.attractor.quiz.dto.game.QuestionForGameDto;
import kg.attractor.quiz.exception.IncorrectInformationException;
import kg.attractor.quiz.model.Question;
import kg.attractor.quiz.service.OptionService;
import kg.attractor.quiz.service.QuestionService;
import kg.attractor.quiz.util.ConvertHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionDao questionDao;
    private final OptionService optionService;

    @Override
    public final void create(QuestionForCreatedDto questionDto, Integer quizId){

        List<OptionForCreatedDto> optionDtos = questionDto.getOptionDtos();

        if (optionDtos == null || optionDtos.isEmpty()) {
            throw new IncorrectInformationException("Отсутствуют ответы на вопросы!");
        }

        if (optionDtos.size() != 4) {
            throw new IncorrectInformationException("На каждый вопрос должно быть ровно 4 варианта ответа!");
        }

        Integer correctCount = Math.toIntExact(optionDtos.stream()
                .filter(OptionForCreatedDto::getIsCorrect)
                .count());

        if (correctCount != 1) {
            throw new IncorrectInformationException("Должен быть только один правильный ответ!");
        }

        Question question = ConvertHelper.convertToQuestion(questionDto);
        Integer questionId = questionDao.saveAndReturnId(question, quizId);

        for(OptionForCreatedDto o : optionDtos) {
            optionService.create(o, questionId);
        }

    }

    @Override
    public List<Question> showAllByQuizId(Integer quizId){
        return questionDao.findAllByQuizId(quizId);
    }

    @Override
    public List<QuestionForGameDto> showQuizQuestionForGame(Integer quizId){
        List<QuestionForGameDto> findQuestions = questionDao.findAllByQuizId(quizId)
                .stream()
                .map(ConvertHelper ::convertToQuestionForGameDto)
                .toList();

        for(QuestionForGameDto q : findQuestions){
            q.setOptionForGameDtos(optionService.showAllQuestionOptionsForGame(q.getId()));
        }

        return findQuestions;
    }
}
