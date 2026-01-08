package kg.attractor.quiz.service;

import jakarta.validation.ConstraintViolation;
import kg.attractor.quiz.model.CustomErrorResponse;
import org.springframework.validation.BindingResult;

import java.util.Set;

public interface ErrorService {

    CustomErrorResponse makeResponse(Exception e);
    CustomErrorResponse makeResponse(BindingResult bindingResult);

    CustomErrorResponse makeResponse(Set<ConstraintViolation<?>> constraintViolations);
}
