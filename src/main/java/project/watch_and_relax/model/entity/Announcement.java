package project.watch_and_relax.model.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Entity
@Table(name="announcements")
public class Announcement extends BaseEntity {
    private Instant createdOn;
    private Instant updatedOn;
    private String title;
    private String description;

    public Announcement() {
    }
    @NotNull
    @Column
    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }
    @Column
    @NotNull
    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }
    @NotNull
    @Column
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    @NotNull
    @Column
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
