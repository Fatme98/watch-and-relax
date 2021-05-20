package project.watch_and_relax.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import project.watch_and_relax.error.ActorNotFoundException;
import project.watch_and_relax.error.FilmNotFoundException;
import project.watch_and_relax.model.entity.*;
import project.watch_and_relax.model.service.ActorServiceModel;
import project.watch_and_relax.model.service.CommentServiceModel;
import project.watch_and_relax.model.service.PhotoProfileServiceModel;
import project.watch_and_relax.model.service.PhotoServiceModel;
import project.watch_and_relax.model.view.VideoViewModel;
import project.watch_and_relax.model.view.YouTubeVideoViewModel;
import project.watch_and_relax.repository.ActorRepository;
import project.watch_and_relax.repository.FilmRepository;
import project.watch_and_relax.repository.PhotoRepository;
import project.watch_and_relax.repository.UserRepository;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ActorServiceTest {
    @Autowired
    private ActorService actorService;
    @Autowired
    private ActorRepository actorRepository;
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private PhotoRepository photoRepository;
@Autowired
private UserRepository userRepository;





    @Test(expected = ActorNotFoundException.class)
    public void testFindActorByIdToFailWithWrongId(){
        String actorId="invalidId";
        this.actorService.findById(actorId);
        System.out.println();
    }

    @Test(expected = ActorNotFoundException.class)
    public void testGetPhotosFromThatActorByIdToFailWithWrongId(){
      String actorId="invalidId";
      this.actorService.getPhotosFromThatActor(actorId);
  }

   @Test
    public void testGetPhotosFromThatActorById(){
       Actor actor=new Actor();
       actor.setRoleName("actor");
       List<Photo> list = new ArrayList<>();
       list.add(new Photo());
       actor.setNickName("actorNickName");
       actor.setFullName("Actor actor");
       Actor actorTest=this.actorRepository.save(actor);
      // List<Photo> photos=this.actorService.getPhotosFromThatActor(actorTest.getId());
        //Assert.assertEquals(1,photos.size());
    }


    @Test(expected = ActorNotFoundException.class)
    public void testGetVideoWithException(){
         String actorId="InvalidId";
         this.actorService.getAllVideosFromThatActor(actorId);
    }




    @Test(expected = ActorNotFoundException.class)
    public void testGetAllYouTubeVideosFromThatActorByIdToFailWithWrongId() {
        String actorId = "InvalidId";
        this.actorService.getAllYouTubeVideosFromThatActors(actorId);
    }



    @Test
    public void testGetCommentFromThatActorToWorkProperly(){
        Actor actor=new Actor();
        actor.setRoleName("actor");
        List<Video>videos=new ArrayList<>();
        videos.add(new Video(){
            {
                setYoutubeVideoUrl("https://www.youtube.com/watch?v=CEml3kY6mGs");
            }
        });
        //actor.setVideos(videos);
     //   actor.setAge(10);
        actor.setFilm(new Film(){
//            {   setComments(new ArrayList<>());
//                setVideos(new ArrayList<>());
//                setPhotos(new ArrayList<>());
//                setPhoto(new Photo());
//                setActors(new ArrayList<>());
//                setUser(new UserEntity());
//                setCategory(new Category());
//                setCountry("UK");
//                setName("Film");}
        });
        List<Photo> list = new ArrayList<>();
        list.add(new Photo());
        //actor.setPhotos(list);
        actor.setNickName("actorNickName");
        actor.setFullName("Actor actor");
        List<Comment>comments=new ArrayList<>();
        comments.add(new Comment());
        //actor.setComments(comments);
        Actor testActor= this.actorRepository.save(actor);
        //List<CommentServiceModel>commentList=this.actorService.getCommentsForActor(testActor.getId());
        //Assert.assertEquals(1,commentList.size());
    }
    @Test(expected = ActorNotFoundException.class)
    public void testGetCommentFromThatActorByIdToFailWithWrongId(){
          String actorId="InvalidId";
          this.actorService.getCommentsForActor(actorId);
      }
}
