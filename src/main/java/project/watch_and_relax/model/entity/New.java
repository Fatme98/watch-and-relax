package project.watch_and_relax.model.entity;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;

@Entity
@Table(name="news")
@Transactional
public class New extends BaseEntity{
    private String newsTitle;
    private String newsBody;
    private Photo photo;
    private String filmId;
    private String actorId;
    public New() {
    }
    @Column(name="news_title",nullable = false)
    @Length(min=3, message = "Title must be more than 3 symbols")
    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }
    @Column(name="newsBody",nullable = false,columnDefinition = "TEXT")
    public String getNewsBody() {
        return newsBody;
    }
@ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public void setNewsBody(String newsBody) {
        this.newsBody = newsBody;
    }

    public String getFilmId() {
        return filmId;
    }

    public void setFilmId(String filmId) {
        this.filmId = filmId;
    }

    public String getActorId() {
        return actorId;
    }

    public void setActorId(String actorId) {
        this.actorId = actorId;
    }
}
