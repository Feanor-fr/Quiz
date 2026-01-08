package kg.attractor.quiz.service;

import kg.attractor.quiz.dto.create.OptionForCreatedDto;
import kg.attractor.quiz.dto.game.OptionForGameDto;

import java.util.List;

public interface OptionService {
    void create(OptionForCreatedDto optionDto, Integer questionId);

    List<OptionForGameDto> showAllQuestionOptionsForGame(Integer questionId);
}
