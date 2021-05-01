package project.watch_and_relax.model.entity;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name="categories")
public class Category extends BaseEntity{
    private CategoryName categoryName;

    public Category() {

    }
    public CategoryName getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(CategoryName categoryName) {
        this.categoryName = categoryName;
    }
}
