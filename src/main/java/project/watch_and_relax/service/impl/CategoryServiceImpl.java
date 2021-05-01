package project.watch_and_relax.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.watch_and_relax.model.entity.Category;
import project.watch_and_relax.model.entity.CategoryName;
import project.watch_and_relax.repository.CategoryRepository;
import project.watch_and_relax.service.CategoryService;

import javax.transaction.Transactional;
import java.util.Arrays;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
@Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void initCategories() {
        if(this.categoryRepository.count()==0){
            Arrays.stream(CategoryName.values()).forEach(c->{
                Category category=new Category();
                category.setCategoryName(c);
                this.categoryRepository.save(category);
            });
        }
    }
    @Override
    public Category findByCategoryName(CategoryName name) {
        return this.categoryRepository.findByCategoryName(name).orElseThrow(IllegalStateException::new);
    }
}
