package project.watch_and_relax.model.view;

import project.watch_and_relax.model.entity.Category;
import project.watch_and_relax.model.entity.CategoryName;

import java.util.List;

public class FilmViewModel {
    private String id;
    private String name;
    private CategoryName category;
    private int yearStarting;
    private int yearEnding;
    private String country;
    private String description;
    private String photoUrl;
    public FilmViewModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

}
