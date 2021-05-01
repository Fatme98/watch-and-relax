package project.watch_and_relax.service;

import project.watch_and_relax.model.entity.Category;
import project.watch_and_relax.model.entity.CategoryName;

public interface CategoryService {
    void initCategories();
    Category findByCategoryName(CategoryName name);
}
