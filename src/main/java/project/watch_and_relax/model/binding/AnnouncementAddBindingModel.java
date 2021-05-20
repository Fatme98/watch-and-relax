package project.watch_and_relax.model.binding;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.Instant;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AnnouncementAddBindingModel {
    private String id;
    private Instant createdOn;
    private Instant updatedOn;
    private String title;
    private String description;

    public AnnouncementAddBindingModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }
@NotBlank
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
@NotBlank
@Size(min=10,message = "The description must be more than 10 characters.")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
