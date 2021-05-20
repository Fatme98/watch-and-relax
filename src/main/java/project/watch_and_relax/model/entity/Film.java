package project.watch_and_relax.model.entity;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name="films")
@Transactional
public class Film extends BaseEntity{
    private String name;
    private Category category;
    private int yearStarting;
    private int yearEnding;
    private String country;
    private String description;
    private Photo photo;
    private UserEntity user;
    private List<Comment> comments;
    private List<Photo>photos;
    private List<Video>videos;
    private List<Actor>actors;
    private List<New>news;
    public Film() {
    }
    @Column(name="name",nullable = false,unique=true)
    @Length(min=3, message = "Film name must be more than 3 symbols")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    //tuk
@ManyToOne
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    @Column(name="year_starting",nullable = false)
    public int getYearStarting() {
        return yearStarting;
    }

    public void setYearStarting(int yearStarting) {
        this.yearStarting = yearStarting;
    }
    @Column(name="year_ending",nullable = false)
    public int getYearEnding() {
        return yearEnding;
    }

    public void setYearEnding(int yearEnding) {
        this.yearEnding = yearEnding;
    }
    @Column(name="country",nullable = false)
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
@ManyToOne

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }
    //tuk
@ManyToOne
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }




    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }


    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }
}
