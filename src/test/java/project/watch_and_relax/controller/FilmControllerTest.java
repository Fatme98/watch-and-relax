package project.watch_and_relax.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import project.watch_and_relax.model.entity.*;
import project.watch_and_relax.repository.ActorRepository;
import project.watch_and_relax.repository.FilmRepository;
import project.watch_and_relax.repository.PhotoRepository;
import project.watch_and_relax.repository.UserRepository;
import project.watch_and_relax.service.FilmService;
import project.watch_and_relax.web.ActorController;
import project.watch_and_relax.web.FilmController;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@Transactional
public class FilmControllerTest {
    @Autowired
    private FilmController filmController;
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
    public void testAdd(){
        String view=this.filmController.add("username",model);
        Assert.assertEquals("add_film",view);
    }
    @Test
    public void testFilmInfo(){
        UserEntity userEntity=new UserEntity();
        userEntity.setEmail("email@abv.bg");
        userEntity.setPassword("123");
        userEntity.setLastName("User");
        userEntity.setFirstName("User");
        userEntity.setRoles(new ArrayList<>());
        userEntity.setUsername("Username");
        userEntity.setPhoto(new Photo());
        UserEntity userEntity1=this.userRepository.save(userEntity);
        Photo photo=new Photo();
        photo.setImageUrl("/images/profile.html");
        Photo photo1=this.photoRepository.save(photo);
        Film film=new Film();
        film.setName("Film");
        film.setCountry("UK");
        film.setUser(userEntity1);
        film.setCategory(new Category());
      //  film.setActors(new ArrayList<>());
        film.setPhoto(photo1);
        film.setPhotos(new ArrayList<>());
        film.setVideos(new ArrayList<>());
        Film film1=this.filmRepository.save(film);
        ModelAndView modelAndView=new ModelAndView();
        modelAndView=this.filmController.filmInfo(film1.getId(),modelAndView,principal);
        Assert.assertEquals("film",modelAndView.getViewName());
    }
    @Test(expected = Exception.class)
    public void testGallery(){
        UserEntity userEntity=new UserEntity();
        userEntity.setEmail("email@abv.bg");
        userEntity.setPassword("123");
        userEntity.setLastName("User");
        userEntity.setFirstName("User");
        userEntity.setRoles(new ArrayList<>());
        userEntity.setUsername("Username");
        userEntity.setPhoto(new Photo());
        UserEntity userEntity1=this.userRepository.save(userEntity);
        Photo photo=new Photo();
        photo.setImageUrl("/images/profile.html");
        Photo photo1=this.photoRepository.save(photo);
        Film film=new Film();
        film.setName("Film");
        film.setCountry("UK");
        film.setUser(userEntity1);
        film.setCategory(new Category());
        film.setPhoto(photo1);
        List<Photo> photos=new ArrayList<>();
        photos.add(new Photo());
        film.setPhotos(photos);
        film.setVideos(new ArrayList<>());
        Film film1=this.filmRepository.save(film);
        ModelAndView modelAndView=new ModelAndView();
        modelAndView=this.filmController.gallery(film1.getId(),modelAndView,principal);
        Assert.assertEquals("gallery",modelAndView.getViewName());
    }
    @Test(expected = Exception.class)
    public void testVideos(){
        UserEntity userEntity=new UserEntity();
        userEntity.setEmail("email@abv.bg");
        userEntity.setPassword("123");
        userEntity.setLastName("User");
        userEntity.setFirstName("User");
        userEntity.setRoles(new ArrayList<>());
        userEntity.setUsername("Username");
        userEntity.setPhoto(new Photo());
        UserEntity userEntity1=this.userRepository.save(userEntity);
        Photo photo=new Photo();
        photo.setImageUrl("/images/profile.html");
        Photo photo1=this.photoRepository.save(photo);
        Film film=new Film();
        film.setName("Film");
        film.setCountry("UK");
        film.setUser(userEntity1);
        film.setCategory(new Category());
        film.setPhoto(photo1);
        List<Photo> photos=new ArrayList<>();
        photos.add(new Photo());
        film.setPhotos(photos);
        film.setVideos(new ArrayList<>());
        Film film1=this.filmRepository.save(film);
        ModelAndView modelAndView=new ModelAndView();
        modelAndView=this.filmController.videos(film1.getId(),modelAndView,principal);
        Assert.assertEquals("videos",modelAndView.getViewName());
    }
    @Test(expected = Exception.class)
    public void testYouTubeVideos(){
        UserEntity userEntity=new UserEntity();
        userEntity.setEmail("email@abv.bg");
        userEntity.setPassword("123");
        userEntity.setLastName("User");
        userEntity.setFirstName("User");
        userEntity.setRoles(new ArrayList<>());
        userEntity.setUsername("Username");
        userEntity.setPhoto(new Photo());
        UserEntity userEntity1=this.userRepository.save(userEntity);
        Photo photo=new Photo();
        photo.setImageUrl("/images/profile.html");
        Photo photo1=this.photoRepository.save(photo);
        Film film=new Film();
        film.setName("Film");
        film.setCountry("UK");
        film.setUser(userEntity1);
        film.setCategory(new Category());
        film.setPhoto(photo1);
        List<Photo> photos=new ArrayList<>();
        photos.add(new Photo());
        film.setPhotos(photos);
        film.setVideos(new ArrayList<>());
        Film film1=this.filmRepository.save(film);
        ModelAndView modelAndView=new ModelAndView();
        modelAndView=this.filmController.youtubeVideos(film1.getId(),modelAndView,principal);
        Assert.assertEquals("youtube",modelAndView.getViewName());
    }
    @Test(expected = Exception.class)
    public void testNewsList(){
        UserEntity userEntity=new UserEntity();
        userEntity.setEmail("email@abv.bg");
        userEntity.setPassword("123");
        userEntity.setLastName("User");
        userEntity.setFirstName("User");
        userEntity.setRoles(new ArrayList<>());
        userEntity.setUsername("Username");
        userEntity.setPhoto(new Photo());
        UserEntity userEntity1=this.userRepository.save(userEntity);
        Photo photo=new Photo();
        photo.setImageUrl("/images/profile.html");
        Photo photo1=this.photoRepository.save(photo);
        Film film=new Film();
        film.setName("Film");
        film.setCountry("UK");
        film.setUser(userEntity1);
        film.setCategory(new Category());
        film.setPhoto(photo1);
        List<Photo> photos=new ArrayList<>();
        photos.add(new Photo());
        film.setPhotos(photos);
        film.setVideos(new ArrayList<>());
        Film film1=this.filmRepository.save(film);
        String modelAndView;
        modelAndView=this.filmController.newsList(film1.getId(),model,principal);
        Assert.assertEquals("news-list",modelAndView);
    }
    @Test(expected = Exception.class)
    public void testSeeComments(){
        UserEntity userEntity=new UserEntity();
        userEntity.setEmail("email@abv.bg");
        userEntity.setPassword("123");
        userEntity.setLastName("User");
        userEntity.setFirstName("User");
        userEntity.setRoles(new ArrayList<>());
        userEntity.setUsername("Username");
        userEntity.setPhoto(new Photo());
        UserEntity userEntity1=this.userRepository.save(userEntity);
        Photo photo=new Photo();
        photo.setImageUrl("/images/profile.html");
        Photo photo1=this.photoRepository.save(photo);
        Film film=new Film();
        film.setName("Film");
        film.setCountry("UK");
        film.setUser(userEntity1);
        film.setCategory(new Category());
        film.setPhoto(photo1);
        List<Photo> photos=new ArrayList<>();
        photos.add(new Photo());
        film.setPhotos(photos);
        film.setVideos(new ArrayList<>());
        Film film1=this.filmRepository.save(film);
        ModelAndView modelAndView=new ModelAndView();
        modelAndView=this.filmController.listOfComments(film1.getId(),modelAndView,principal);
        Assert.assertEquals("comments",modelAndView.getViewName());
    }
    @Test(expected = Exception.class)
    public void testDelete(){
        UserEntity userEntity=new UserEntity();
        userEntity.setEmail("email@abv.bg");
        userEntity.setPassword("123");
        userEntity.setLastName("User");
        userEntity.setFirstName("User");
        userEntity.setRoles(new ArrayList<>());
        userEntity.setUsername("Username");
        userEntity.setPhoto(new Photo());
        UserEntity userEntity1=this.userRepository.save(userEntity);
        Photo photo=new Photo();
        photo.setImageUrl("/images/profile.html");
        Photo photo1=this.photoRepository.save(photo);
        Film film=new Film();
        film.setName("Film");
        film.setCountry("UK");
        film.setUser(userEntity1);
        film.setCategory(new Category());
        film.setPhoto(photo1);
        List<Photo> photos=new ArrayList<>();
        photos.add(new Photo());
        film.setPhotos(photos);
        film.setVideos(new ArrayList<>());
        Film film1=this.filmRepository.save(film);

        String view = this.filmController.deleteFilm(film1.getId());
        Assert.assertEquals("redirect:/home",view);


    }
    @Test
    public void testMakeFavourite(){}
}
