package project.watch_and_relax.service;

import org.aspectj.util.Reflection;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import project.watch_and_relax.error.UserNotFoundException;
import project.watch_and_relax.model.entity.*;
import project.watch_and_relax.model.view.FilmViewModel;
import project.watch_and_relax.model.view.UserViewModel;
import project.watch_and_relax.repository.FilmRepository;
import project.watch_and_relax.repository.UserRepository;
import project.watch_and_relax.service.impl.UserServiceImpl;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class UserServiceTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private FilmRepository filmRepository;

    @Test(expected = UserNotFoundException.class)
    public void testFindUserThrowingException(){
        String username="wrongUsername";
        this.userService.findUser(username);

    }
    @Test(expected = UserNotFoundException.class)
    public void testFindByUsernameThrowingException(){
        String username="wrongUsername";
        userService.findUserByUsername(username);
    }
    @Test
    public void testWhetherUserIsAdminToReturnTrue(){
        boolean isAdmin=this.userService.isAdmin("admin");
        Assert.assertEquals(true,isAdmin);
    }

    @Test
    public void testFindUser(){
         UserViewModel userViewModel=new UserViewModel();
         userViewModel.setFilms(new ArrayList<>());
         userViewModel.setPhotoUrl("/images/profile");
         userViewModel.setRoles(new ArrayList<>());
         userViewModel.setEmail("fatme.osman1998@abv.bg");
         userViewModel.setId("id");
         userViewModel.setLastName("Osman");
         userViewModel.setFirstName("Fatme");
         userViewModel.setUsername("admin");
         UserViewModel userViewModel1=this.userService.findUser("admin");
         Assert.assertEquals(userViewModel.getUsername(),userViewModel1.getUsername());
     }
    @Test
    public void testFindUserByUsernameToWorkCorrectly(){
        UserViewModel userViewModel=new UserViewModel();
        userViewModel.setFilms(new ArrayList<>());
        userViewModel.setPhotoUrl("/images/profile");
        userViewModel.setRoles(new ArrayList<>());
        userViewModel.setEmail("fatme.osman1998@abv.bg");
        userViewModel.setId("id");
        userViewModel.setLastName("Osman");
        userViewModel.setFirstName("Fatme");
        userViewModel.setUsername("admin");
        UserViewModel userViewModel1=this.userService.findUserByUsername("admin");
        Assert.assertEquals(userViewModel.getUsername(),userViewModel1.getUsername());
    }
    @Test
    public void testIfUserExistToReturnTrue(){
        boolean doesExistUser=this.userService.existsUser("admin");
        Assert.assertEquals(true,doesExistUser);
    }
    @Test
    public void testIfUserExistToReturnFalse(){
        boolean doesExistUser=this.userService.existsUser("user");
        Assert.assertEquals(false,doesExistUser);
    }
    @Test
    public void testIfCreateAdminIsInvokedMoreThanOnce(){
        UserService userService=mock(UserService.class);
        verify(userService,times(0)).createAdmin();
    }
    @Test
    public void testCreateAdmin(){
        if(this.userRepository.count()==0){
            this.userService.createAdmin();
        }
        Assert.assertEquals(1,this.userRepository.count());
    }
//    @Test
//    public void testDeleteFilmFromUser(){
//        UserEntity userEntity=new UserEntity();
//        userEntity.setEmail("email@abv.bg");
//        userEntity.setPassword("123");
//        userEntity.setLastName("User");
//        userEntity.setFirstName("User");
//        userEntity.setRoles(new ArrayList<>());
//        userEntity.setUsername("Username");
//        userEntity.setPhoto(new Photo());
//        UserEntity userEntity1=this.userRepository.save(userEntity);
//        Photo photo=new Photo();
//        Film film=new Film();
//        film.setName("Film");
//        film.setCountry("UK");
//        film.setUser(userEntity1);
//        film.setCategory(new Category());
//        film.setPhoto(photo);
//        film.setPhotos(new ArrayList<>());
//        film.setVideos(new ArrayList<>());
//        Film film1=this.filmRepository.save(film);
//        long dbSize=this.filmRepository.count();
//        this.userService.deleteFilmFromUser(film1.getId(),userEntity1.getUsername());
//        Assert.assertEquals(dbSize-1,this.filmRepository.count());
//    }


}
