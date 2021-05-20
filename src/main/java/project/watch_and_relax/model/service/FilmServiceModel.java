package project.watch_and_relax.model.service;

import project.watch_and_relax.model.entity.Category;

import java.util.List;

public class FilmServiceModel {
    private String id;
    private String name;
    private Category category;
    private int yearStarting;
    private int yearEnding;
    private String country;
    private String description;
    private String photoUrl;
    private String username;
    private List<ActorServiceModel>actorServiceModels;
    public FilmServiceModel() {
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<ActorServiceModel> getActorServiceModels() {
        return actorServiceModels;
    }

    public void setActorServiceModels(List<ActorServiceModel> actorServiceModels) {
        this.actorServiceModels = actorServiceModels;
    }
}
