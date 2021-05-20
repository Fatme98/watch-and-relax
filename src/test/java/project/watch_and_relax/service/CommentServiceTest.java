package project.watch_and_relax.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import project.watch_and_relax.error.ActorNotFoundException;
import project.watch_and_relax.error.FilmNotFoundException;
import project.watch_and_relax.model.entity.Comment;
import project.watch_and_relax.model.entity.UserEntity;
import project.watch_and_relax.model.service.CommentServiceModel;
import project.watch_and_relax.repository.CommentRepository;
import project.watch_and_relax.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CommentServiceTest {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserRepository userRepository;
    @Test
    public void testAddCommentToWokrProperly(){
        CommentServiceModel commentServiceModel=new CommentServiceModel();
        commentServiceModel.setComment("film");
        long dbCount=this.commentRepository.count();
        this.commentService.addComment(commentServiceModel);
        Assert.assertEquals(dbCount+1,this.commentRepository.count());
    }
  @Test
    public void testAddCommentToActor(){
        CommentServiceModel commentServiceModel=new CommentServiceModel();
        commentServiceModel.setComment("film");
        commentServiceModel.setActor_id("id");
        long dbCount=this.commentRepository.count();
        this.commentService.addComment(commentServiceModel);
        long dbCount1=this.commentRepository.count();
        Assert.assertEquals(dbCount+1,this.commentRepository.count());
    }
   @Test
    public void testAddCommentTFilm(){
        CommentServiceModel commentServiceModel=new CommentServiceModel();
        commentServiceModel.setComment("film");
        commentServiceModel.setFilm_id("id");
        long dbCount=this.commentRepository.count();
        this.commentService.addComment(commentServiceModel);
        long dbCount1=this.commentRepository.count();
        Assert.assertEquals(dbCount+1,this.commentRepository.count());
    }
    @Test
    public void testGetAllCommentsByActorId(){
        Comment comment=new Comment();
        comment.setActorId("id");
        comment.setComment("film");
        comment.setUsername("admin");
        this.commentRepository.save(comment);
        List<CommentServiceModel> comments = this.commentService.getAllCommentsByActorId("id");
        Assert.assertEquals(1,comments.size());
    }
    @Test
    public void testGetAllCommentsByFilmId(){
        Comment comment=new Comment();
        comment.setFilmId("id1");
        comment.setComment("film1");
        comment.setUsername("admin");
        this.commentRepository.save(comment);
        List<CommentServiceModel> comments = this.commentService.getAllCommentsByFilmId("id1");
        Assert.assertEquals(1,comments.size());
    }
    @Test
    public void testDeleteById(){
        Comment comment=new Comment();
        comment.setFilmId("id1");
        comment.setComment("film1");
        comment.setUsername("admin");
        Comment save = this.commentRepository.save(comment);
        long dbSize=this.commentRepository.count();
        this.commentService.deleteById(save.getId());
        Assert.assertEquals(dbSize-1,this.commentRepository.count());
    }

}
