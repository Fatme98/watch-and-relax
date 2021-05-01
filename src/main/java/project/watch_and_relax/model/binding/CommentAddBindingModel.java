package project.watch_and_relax.model.binding;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

public class CommentAddBindingModel {
    private String comment;

    public CommentAddBindingModel() {
    }
@NotBlank

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
