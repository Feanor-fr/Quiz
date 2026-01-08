package kg.attractor.quiz.service.impl;

import jakarta.validation.ConstraintViolation;
import kg.attractor.quiz.model.CustomErrorResponse;
import kg.attractor.quiz.service.ErrorService;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.*;

@Service
public class ErrorServiceImpl implements ErrorService {

    @Override
    public CustomErrorResponse makeResponse(Exception e) {
        String message = e.getMessage();
        CustomErrorResponse errorResponse = new CustomErrorResponse();
        errorResponse.setError(message);
        Map<String, List<String>> reasons = new HashMap<>();
        reasons.put("reasons", Arrays.stream(e.getStackTrace())
                .map(StackTraceElement::toString)
                .toList());
        errorResponse.setReasons(reasons);
        return errorResponse;
    }

    @Override
    public CustomErrorResponse makeResponse(BindingResult bindingResult) {
        Map<String, List<String>> reasons = new HashMap<>();
        bindingResult.getFieldErrors().stream()
                .filter(error -> error.getDefaultMessage() != null)
                .forEach(err -> {
                    if (!reasons.containsKey(err.getField())) {
                        reasons.put(err.getField(), new ArrayList<>());
                    }
                    reasons.get(err.getField()).add(err.getDefaultMessage());
                });
        return CustomErrorResponse.builder()
                .error("Validation Error")
                .reasons(reasons)
                .build();
    }

    @Override
    public CustomErrorResponse makeResponse(Set<ConstraintViolation<?>> constraintViolations) {
        Map<String, List<String>> reasons = new HashMap<>();
        constraintViolations.forEach(violation -> {
            String fieldName = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();
            reasons.computeIfAbsent(fieldName, k -> new ArrayList<>()).add(errorMessage);
        });

        return CustomErrorResponse.builder()
                .error("Validation Error")
                .reasons(reasons)
                .build();
    }

}
