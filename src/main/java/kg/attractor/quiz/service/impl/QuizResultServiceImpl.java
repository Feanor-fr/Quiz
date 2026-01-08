package kg.attractor.quiz.service.impl;

import kg.attractor.quiz.dao.QuizResultDao;
import kg.attractor.quiz.model.QuizResult;
import kg.attractor.quiz.service.QuizResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuizResultServiceImpl implements QuizResultService {
    private final QuizResultDao quizResultDao;

    @Override
    public void create(QuizResult quizResult){
        quizResultDao.save(quizResult);
    }


}
