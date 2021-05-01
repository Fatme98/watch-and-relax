package project.watch_and_relax.model.entity;

import javax.persistence.*;
import javax.transaction.Transactional;

@Entity
@Table(name="comments")
@Transactional
public class Comment extends BaseEntity{
    private String comment;
    private String username;
    private String filmId;
    private String actorId;
    public Comment() {
    }
@Column(name="comment",nullable = false)
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

