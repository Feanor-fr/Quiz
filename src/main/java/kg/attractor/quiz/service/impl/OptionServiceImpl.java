package kg.attractor.quiz.service.impl;

import kg.attractor.quiz.dao.OptionDao;
import kg.attractor.quiz.dto.create.OptionForCreatedDto;
import kg.attractor.quiz.dto.game.OptionForGameDto;
import kg.attractor.quiz.model.Option;
import kg.attractor.quiz.service.OptionService;
import kg.attractor.quiz.util.ConvertHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OptionServiceImpl implements OptionService{
    private final OptionDao optionDao;

    @Override
    public void create(OptionForCreatedDto optionDto, Integer questionId){
        Option option = ConvertHelper.convertToOption(optionDto);
        optionDao.save(option, questionId);
    }

    @Override
    public Optional<Option> getOptionById(Integer id){
        return optionDao.getOptionById(id);
    }

    @Override
    public List<OptionForGameDto> showAllQuestionOptionsForGame(Integer questionId){
        return optionDao.findAllByQuestionId(questionId)
                .stream()
                .map(ConvertHelper ::convertToOptionForGameDto)
                .toList();
    }
}
