package project.watch_and_relax.service;

import project.watch_and_relax.model.service.CommentServiceModel;
import project.watch_and_relax.model.view.CommentViewModel;

import java.util.List;

public interface CommentService {
    void addComment(CommentServiceModel commentServiceModel);
    List<CommentServiceModel>getAllCommentsByFilmId(String id);
    List<CommentServiceModel>getAllCommentsByActorId(String id);
    void deleteById(String id);
}
