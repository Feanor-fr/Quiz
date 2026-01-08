package kg.attractor.quiz.service;

import kg.attractor.quiz.dto.create.OptionForCreatedDto;

public interface OptionService {
    void create(OptionForCreatedDto optionDto, Integer questionId);
}
