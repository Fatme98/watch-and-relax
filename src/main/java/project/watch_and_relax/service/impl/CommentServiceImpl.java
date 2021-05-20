package project.watch_and_relax.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import project.watch_and_relax.model.entity.Comment;
import project.watch_and_relax.model.service.CommentServiceModel;
import project.watch_and_relax.model.view.UserViewModel;
import project.watch_and_relax.repository.CommentRepository;
import project.watch_and_relax.service.CommentService;
import project.watch_and_relax.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private final ModelMapper modelMapper;
    private final CommentRepository commentRepository;
    private final UserService userService;


    public CommentServiceImpl(ModelMapper modelMapper, CommentRepository commentRepository,
                              UserService userService) {
        this.modelMapper = modelMapper;
        this.commentRepository = commentRepository;
        this.userService = userService;
    }

    @Override
    public void addComment(CommentServiceModel commentServiceModel) {
        Comment comment=this.modelMapper.map(commentServiceModel,Comment.class);
        if(commentServiceModel.getActor_id()!=null){
           comment.setActorId(commentServiceModel.getActor_id());
        }else if(commentServiceModel.getFilm_id()!=null){
           comment.setFilmId(commentServiceModel.getFilm_id());
        }
        comment=this.commentRepository.saveAndFlush(comment);
    }

    @Override
    public List<CommentServiceModel> getAllCommentsByFilmId(String id) {
        List<Comment>comments=this.commentRepository.findByFilmId(id);
        List<CommentServiceModel>commentServiceModels=new ArrayList<>();
        for (Comment comment : comments) {
            CommentServiceModel commentServiceModel=new CommentServiceModel();
            commentServiceModel.setId(comment.getId());
            commentServiceModel.setComment(comment.getComment());
            commentServiceModel.setUsername(comment.getUsername());
            UserViewModel user = this.userService.findUser(comment.getUsername());
            commentServiceModel.setUserPhotoUrl(user.getPhotoUrl());
            commentServiceModels.add(commentServiceModel);
        }
        return commentServiceModels;
    }

    @Override
    public List<CommentServiceModel> getAllCommentsByActorId(String id) {
        List<Comment>comments=this.commentRepository.findByActorId(id);
        List<CommentServiceModel>commentServiceModels=new ArrayList<>();
        for (Comment comment : comments) {
            CommentServiceModel commentServiceModel=new CommentServiceModel();
            commentServiceModel.setId(comment.getId());
            commentServiceModel.setComment(comment.getComment());
            commentServiceModel.setUsername(comment.getUsername());
            UserViewModel user = this.userService.findUser(comment.getUsername());
            commentServiceModel.setUserPhotoUrl(user.getPhotoUrl());
            commentServiceModels.add(commentServiceModel);
        }
        return commentServiceModels;
    }

    @Override
    public void deleteById(String id) {
        this.commentRepository.deleteById(id);
    }
}
