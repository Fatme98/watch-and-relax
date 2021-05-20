package project.watch_and_relax.model.service;

import project.watch_and_relax.model.entity.CategoryName;

public class CategoryServiceModel {
    private CategoryName categoryName;

    public CategoryServiceModel() {
    }

    public CategoryName getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(CategoryName categoryName) {
        this.categoryName = categoryName;
    }
}
