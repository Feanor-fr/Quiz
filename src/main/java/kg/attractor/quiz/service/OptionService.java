package kg.attractor.quiz.service;

import kg.attractor.quiz.dto.create.OptionForCreatedDto;
import kg.attractor.quiz.dto.game.OptionForGameDto;
import kg.attractor.quiz.model.Option;

import java.util.List;
import java.util.Optional;

public interface OptionService {
    void create(OptionForCreatedDto optionDto, Integer questionId);
    Optional<Option> getOptionById(Integer id);
    List<OptionForGameDto> showAllQuestionOptionsForGame(Integer questionId);
}
