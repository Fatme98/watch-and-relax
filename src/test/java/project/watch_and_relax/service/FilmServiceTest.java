package project.watch_and_relax.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import project.watch_and_relax.error.FilmNotFoundException;
import project.watch_and_relax.model.entity.*;
import project.watch_and_relax.model.service.ActorServiceModel;
import project.watch_and_relax.model.service.CommentServiceModel;
import project.watch_and_relax.model.service.FilmServiceModel;
import project.watch_and_relax.model.service.PhotoServiceModel;
import project.watch_and_relax.model.view.FilmViewModel;
import project.watch_and_relax.repository.ActorRepository;
import project.watch_and_relax.repository.FilmRepository;
import project.watch_and_relax.repository.PhotoRepository;
import project.watch_and_relax.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class FilmServiceTest {
    @Autowired
    private FilmService filmService;
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private PhotoRepository photoRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ActorRepository actorRepository;
    @Test(expected = FilmNotFoundException.class)
    public void testFindByIdToBeWithWrongIdANdToThrowException(){
        String filmId="InvalidId";
        this.filmService.findById(filmId);
    }
    @Test
    public void testFindByIdToWorkCorrectly(){
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
        film.setPhotos(new ArrayList<>());
        film.setVideos(new ArrayList<>());
        Film film1=this.filmRepository.save(film);
        FilmServiceModel byId = this.filmService.findById(film.getId());
        Assert.assertEquals(film1.getName(),byId.getName());
    }
    @Test(expected = FilmNotFoundException.class)
    public void testFindByNameToBeWithWrongnAMEANdToThrowException(){
        String filmName="Invalid";
        this.filmService.findByName(filmName);
    }

    @Test(expected = FilmNotFoundException.class)
    public void testGetAllPicturesFromThatFilmToBeWithWrongIdANdToThrowException(){
        String filmId="InvalidId";
        this.filmService.getAllPicturesFromThatFilm(filmId);
    }


    @Test(expected = FilmNotFoundException.class)
    public void testGetAllVideosFromThatFilmToBeWithWrongIdANdToThrowException(){
        String filmId="InvalidId";
        this.filmService.getAllVideosFromThatFilm(filmId);
    }

    @Test(expected = FilmNotFoundException.class)
    public void testGetAllYouTubeVideosFromThatFilmToBeWithWrongIdANdToThrowException(){
        String filmId="InvalidId";
        this.filmService.getAllYouTubeVideosFromThatFilm(filmId);
    }

    @Test(expected = FilmNotFoundException.class)
    public void testGetAllActorsFromThatFilmToBeWithWrongIdANdToThrowException(){
        String filmId="InvalidId";
        this.filmService.getAllActorsFromThatFilm(filmId);
    }

    @Test(expected = FilmNotFoundException.class)
    public void testGetAllCommentsFromThatFilmToBeWithWrongIdANdToThrowException(){
        String filmId="InvalidId";
        this.filmService.getCommentsForFilm(filmId);
    }



}
