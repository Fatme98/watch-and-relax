package project.watch_and_relax.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.AutoConfigureDataR2dbc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import project.watch_and_relax.error.ActorNotFoundException;
import project.watch_and_relax.model.binding.CommentAddBindingModel;
import project.watch_and_relax.model.entity.*;
import project.watch_and_relax.repository.ActorRepository;
import project.watch_and_relax.repository.FilmRepository;
import project.watch_and_relax.repository.PhotoRepository;
import project.watch_and_relax.repository.UserRepository;
import project.watch_and_relax.service.ActorService;
import project.watch_and_relax.service.FilmService;
import project.watch_and_relax.web.ActorController;

import javax.lang.model.element.ModuleElement;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@Transactional
public class ActorControllerTest {
    @Autowired
    private ActorController actorController;
    @Autowired
    private HttpSession httpSession;
    @Mock
    private Principal principal;
    @Mock
    private Model model;
    @Autowired
    private FilmService filmService;
    @Autowired
    private ActorRepository actorRepository;
    @Autowired
    private PhotoRepository photoRepository;
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private UserRepository userRepository;
    @Test
    public void testAddMethod(){
        Film film = new Film();
        film.setPhoto(new Photo());

        UserEntity userEntity=new UserEntity();
        userEntity.setEmail("email@abv.bg");
        userEntity.setPassword("123");
        userEntity.setLastName("User");
        userEntity.setFirstName("User");
        userEntity.setRoles(new ArrayList<>());
        userEntity.setUsername("Username");
        userEntity.setPhoto(new Photo());
        UserEntity userEntity1=this.userRepository.save(userEntity);
        film.setUser(userEntity1);
        film.setCategory(new Category());
        film.setCountry("UK");
        film.setName("Film");
        Film film1=this.filmRepository.save(film);
        Photo photo=new Photo();
        photo.setImageUrl("/images/profile.html");
        Photo photo1=this.photoRepository.save(photo);
        Actor actor=new Actor();
        actor.setRoleName("actor");
        actor.setFilm(film1);
        actor.setNickName("actorNickName");
        actor.setFullName("Actor actor");
        actor.setPhoto(photo1);
        Actor actorTest=this.actorRepository.save(actor);
        String view=this.actorController.add(actorTest.getId(),model,principal);
        Assert.assertEquals("add_actor",view);
    }

    @Test(expected = ActorNotFoundException.class)
    public void testCommentForException(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView=this.actorController.comment("id",new CommentAddBindingModel(),modelAndView,principal);
        Assert.assertEquals("home",modelAndView.getViewName());
    }



    @Test
    public void testActorInformation(){
        ModelAndView modelAndView=new ModelAndView();
        Film film = new Film();
        film.setPhoto(new Photo());
        UserEntity userEntity=new UserEntity();
        userEntity.setEmail("email@abv.bg");
        userEntity.setPassword("123");
        userEntity.setLastName("User");
        userEntity.setFirstName("User");
        userEntity.setRoles(new ArrayList<>());
        userEntity.setUsername("Username");
        userEntity.setPhoto(new Photo());
        UserEntity userEntity1=this.userRepository.save(userEntity);
        film.setUser(userEntity1);
        film.setCategory(new Category());
        film.setCountry("UK");
        film.setName("Film");
        Film film1=this.filmRepository.save(film);
        Photo photo=new Photo();
        photo.setImageUrl("/images/profile.html");
        Photo photo1=this.photoRepository.save(photo);
        Actor actor=new Actor();
        actor.setRoleName("actor");
        actor.setFilm(film1);
        actor.setNickName("actorNickName");
        actor.setFullName("Actor actor");
        actor.setPhoto(photo1);
        Actor actorTest=this.actorRepository.save(actor);
        modelAndView=this.actorController.actorInformation(actorTest.getId(),modelAndView,principal);
        Assert.assertEquals("actor_info",modelAndView.getViewName());
    }
    @Test(expected = Exception.class)
    public void testListOfCommentsMethod(){
        ModelAndView modelAndView=new ModelAndView();
        Film film = new Film();
        film.setPhoto(new Photo());
        UserEntity userEntity=new UserEntity();
        userEntity.setEmail("email@abv.bg");
        userEntity.setPassword("123");
        userEntity.setLastName("User");
        userEntity.setFirstName("User");
        userEntity.setRoles(new ArrayList<>());
        userEntity.setUsername("Username");
        userEntity.setPhoto(new Photo());
        UserEntity userEntity1=this.userRepository.save(userEntity);
        film.setUser(userEntity1);
        film.setCategory(new Category());
        film.setCountry("UK");
        film.setName("Film");
        Film film1=this.filmRepository.save(film);
        Photo photo=new Photo();
        photo.setImageUrl("/images/profile.html");
        Photo photo1=this.photoRepository.save(photo);
        Actor actor=new Actor();
        actor.setRoleName("actor");
        actor.setFilm(film1);
        actor.setNickName("actorNickName");
        actor.setFullName("Actor actor");
        actor.setPhoto(photo1);
        Actor actorTest=this.actorRepository.save(actor);
        ModelAndView modelAndView1 = this.actorController.listOfComments(actorTest.getId(), modelAndView, principal);
        Assert.assertEquals("comments",modelAndView.getViewName());
    }
    @Test
    public void testDeleteMethod(){
        ModelAndView modelAndView=new ModelAndView();
        Film film = new Film();
        film.setPhoto(new Photo());
        UserEntity userEntity=new UserEntity();
        userEntity.setEmail("email@abv.bg");
        userEntity.setPassword("123");
        userEntity.setLastName("User");
        userEntity.setFirstName("User");
        userEntity.setRoles(new ArrayList<>());
        userEntity.setUsername("Username");
        userEntity.setPhoto(new Photo());
        UserEntity userEntity1=this.userRepository.save(userEntity);
        film.setUser(userEntity1);
        film.setCategory(new Category());
        film.setCountry("UK");
        film.setName("Film");
        Film film1=this.filmRepository.save(film);
        Photo photo=new Photo();
        photo.setImageUrl("/images/profile.html");
        Photo photo1=this.photoRepository.save(photo);
        Actor actor=new Actor();
        actor.setRoleName("actor");
        actor.setFilm(film1);
        actor.setNickName("actorNickName");
        actor.setFullName("Actor actor");
        actor.setPhoto(photo1);
        Actor actorTest=this.actorRepository.save(actor);
        String view = this.actorController.deleteActor(actorTest.getId());
        Assert.assertEquals("redirect:/home",view);

    }
    @Test(expected = Exception.class)
    public void testNewsListMethod(){
        ModelAndView modelAndView=new ModelAndView();
        Film film = new Film();
        film.setPhoto(new Photo());
        UserEntity userEntity=new UserEntity();
        userEntity.setEmail("email@abv.bg");
        userEntity.setPassword("123");
        userEntity.setLastName("User");
        userEntity.setFirstName("User");
        userEntity.setRoles(new ArrayList<>());
        userEntity.setUsername("Username");
        userEntity.setPhoto(new Photo());
        UserEntity userEntity1=this.userRepository.save(userEntity);
        film.setUser(userEntity1);
        film.setCategory(new Category());
        film.setCountry("UK");
        film.setName("Film");
        Film film1=this.filmRepository.save(film);
        Photo photo=new Photo();
        photo.setImageUrl("/images/profile.html");
        Photo photo1=this.photoRepository.save(photo);
        Actor actor=new Actor();
        actor.setRoleName("actor");
        actor.setFilm(film1);
        actor.setNickName("actorNickName");
        actor.setFullName("Actor actor");
        actor.setPhoto(photo1);
        Actor actorTest=this.actorRepository.save(actor);
        String view = this.actorController.newsList(actorTest.getId(), model, principal);

    }
    @Test(expected = Exception.class)
    public void testVideos(){
        ModelAndView modelAndView=new ModelAndView();
        Film film = new Film();
        film.setPhoto(new Photo());
        UserEntity userEntity=new UserEntity();
        userEntity.setEmail("email@abv.bg");
        userEntity.setPassword("123");
        userEntity.setLastName("User");
        userEntity.setFirstName("User");
        userEntity.setRoles(new ArrayList<>());
        userEntity.setUsername("Username");
        userEntity.setPhoto(new Photo());
        UserEntity userEntity1=this.userRepository.save(userEntity);
        film.setUser(userEntity1);
        film.setCategory(new Category());
        film.setCountry("UK");
        film.setName("Film");
        Film film1=this.filmRepository.save(film);
        Photo photo=new Photo();
        photo.setImageUrl("/images/profile.html");
        Photo photo1=this.photoRepository.save(photo);
        Actor actor=new Actor();
        actor.setRoleName("actor");
        actor.setFilm(film1);
        actor.setNickName("actorNickName");
        actor.setFullName("Actor actor");
        actor.setPhoto(photo1);
        Actor actorTest=this.actorRepository.save(actor);
        ModelAndView view = this.actorController.videos(actorTest.getId(),modelAndView,principal);
    }
    @Test(expected = Exception.class)
    public void testYouTubeVideos(){
        ModelAndView modelAndView=new ModelAndView();
        Film film = new Film();
        film.setPhoto(new Photo());
        UserEntity userEntity=new UserEntity();
        userEntity.setEmail("email@abv.bg");
        userEntity.setPassword("123");
        userEntity.setLastName("User");
        userEntity.setFirstName("User");
        userEntity.setRoles(new ArrayList<>());
        userEntity.setUsername("Username");
        userEntity.setPhoto(new Photo());
        UserEntity userEntity1=this.userRepository.save(userEntity);
        film.setUser(userEntity1);
        film.setCategory(new Category());
        film.setCountry("UK");
        film.setName("Film");
        Film film1=this.filmRepository.save(film);
        Photo photo=new Photo();
        photo.setImageUrl("/images/profile.html");
        Photo photo1=this.photoRepository.save(photo);
        Actor actor=new Actor();
        actor.setRoleName("actor");
        actor.setFilm(film1);
        actor.setNickName("actorNickName");
        actor.setFullName("Actor actor");
        actor.setPhoto(photo1);
        Actor actorTest=this.actorRepository.save(actor);
        ModelAndView view = this.actorController.youtubeVideos(actorTest.getId(), modelAndView, principal);
    }
    @Test(expected = Exception.class)
    public void testGallery(){
        ModelAndView modelAndView=new ModelAndView();
        Film film = new Film();
        film.setPhoto(new Photo());
        UserEntity userEntity=new UserEntity();
        userEntity.setEmail("email@abv.bg");
        userEntity.setPassword("123");
        userEntity.setLastName("User");
        userEntity.setFirstName("User");
        userEntity.setRoles(new ArrayList<>());
        userEntity.setUsername("Username");
        userEntity.setPhoto(new Photo());
        UserEntity userEntity1=this.userRepository.save(userEntity);
        film.setUser(userEntity1);
        film.setCategory(new Category());
        film.setCountry("UK");
        film.setName("Film");
        Film film1=this.filmRepository.save(film);
        Photo photo=new Photo();
        photo.setImageUrl("/images/profile.html");
        Photo photo1=this.photoRepository.save(photo);
        Actor actor=new Actor();
        actor.setRoleName("actor");
        actor.setFilm(film1);
        actor.setNickName("actorNickName");
        actor.setFullName("Actor actor");
        actor.setPhoto(photo1);
        Actor actorTest=this.actorRepository.save(actor);
        ModelAndView view = this.actorController.gallery(actorTest.getId(), modelAndView, principal);
    }
}
