package kg.attractor.quiz.service.impl;

import kg.attractor.quiz.dao.CategoryDao;
import kg.attractor.quiz.exception.CategoryNotFoundException;
import kg.attractor.quiz.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryDao categoryDao;

    @Override
    public Integer findCategoryIdById(Integer id){
        return categoryDao.findCategoryIdById(id).orElseThrow(CategoryNotFoundException::new);
    }
}
