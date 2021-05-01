package project.watch_and_relax.model.binding;

import org.hibernate.validator.constraints.Length;
import project.watch_and_relax.model.entity.Category;
import project.watch_and_relax.model.entity.CategoryName;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

public class FilmAddBindingModel {
    private String name;
    private CategoryName category;
    private int yearStarting;
    private int yearEnding;
    private String country;
    private String description;

    public FilmAddBindingModel() {
    }
    @NotBlank
    @Length(min=3, message = "Film name must be more than 3 symbols")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryName getCategory() {
        return category;
    }

    public void setCategory(CategoryName category) {
        this.category = category;
    }
    public int getYearStarting() {
        return yearStarting;
    }

    public void setYearStarting(int yearStarting) {
        this.yearStarting = yearStarting;
    }
    public int getYearEnding() {
        return yearEnding;
    }

    public void setYearEnding(int yearEnding) {
        this.yearEnding = yearEnding;
    }
    @NotBlank
    @Length(min=1, message = "Country must be more than 1 symbols")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    @Column(name="description",nullable = false,columnDefinition = "TEXT")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
