package project.watch_and_relax.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.watch_and_relax.error.UserNotFoundException;
import project.watch_and_relax.model.binding.UserLoginBindingModel;
import project.watch_and_relax.model.binding.UserRegisterBindingModel;
import project.watch_and_relax.model.entity.Photo;
import project.watch_and_relax.model.entity.UserEntity;
import project.watch_and_relax.model.view.UserViewModel;
import project.watch_and_relax.repository.PhotoRepository;
import project.watch_and_relax.repository.UserRepository;
import project.watch_and_relax.service.UserService;
import project.watch_and_relax.web.PhotoController;
import project.watch_and_relax.web.UserController;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.security.Principal;
import java.util.ArrayList;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@Transactional
public class UserControllerTest {
    @Autowired
    private UserController userController;
    @Autowired
    private HttpSession httpSession;
    @Mock
    private Principal principal;
    @Mock
    private Model model;
    @Mock
    private BindingResult bindingResult;
    @Mock
    private RedirectAttributes redirectAttributes;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PhotoRepository photoRepository;
    @Test
    public void testRegister(){
        String view=this.userController.register(model);
        Assert.assertEquals("register",view);
    }
    @Test
    public void testLogin(){
        String view=this.userController.login(model);
        Assert.assertEquals("login",view);
    }
    @Test
    public void testRegisterConfirm(){
        UserRegisterBindingModel userRegisterBindingModel=new UserRegisterBindingModel();
        userRegisterBindingModel.setUsername("user");
        userRegisterBindingModel.setPassword("123");
        userRegisterBindingModel.setConfirmPassword("123");
    //    userRegisterBindingModel.setAge(1);
        userRegisterBindingModel.setEmail("email@abv.bg");
        userRegisterBindingModel.setFirstName("User");
        userRegisterBindingModel.setLastName("User");
        ModelAndView modelAndView=new ModelAndView();
        String view=this.userController.registerConfirm(null,
                userRegisterBindingModel,bindingResult,httpSession,redirectAttributes,model);
        Assert.assertEquals("index",view);
    }
    @Test
    public void testRegisterConfirmToHaveAnError(){
        UserRegisterBindingModel userRegisterBindingModel=new UserRegisterBindingModel();
        userRegisterBindingModel.setUsername("user");
        userRegisterBindingModel.setPassword("123");
        userRegisterBindingModel.setConfirmPassword("123456");
      //  userRegisterBindingModel.setAge(1);
        userRegisterBindingModel.setEmail("email@abv.bg");
        userRegisterBindingModel.setFirstName("User");
        userRegisterBindingModel.setLastName("User");
        ModelAndView modelAndView=new ModelAndView();
        String view=this.userController.registerConfirm(null,
                userRegisterBindingModel,bindingResult,httpSession,redirectAttributes,model);
        Assert.assertEquals("register",view);
    }
    @Test(expected = UserNotFoundException.class)
    public void testLoginConfirm(){
        Photo photo=new Photo();
        photo.setImageUrl("/images/profile.html");
        Photo photo1=this.photoRepository.save(photo);
        UserEntity userEntity=new UserEntity();
        userEntity.setEmail("email@abv.bg");
        userEntity.setPassword("123");
        userEntity.setLastName("User");
        userEntity.setFirstName("User");
        userEntity.setRoles(new ArrayList<>());
        userEntity.setUsername("Username");
        userEntity.setPhoto(photo1);
        UserEntity userEntity1=this.userRepository.save(userEntity);
        UserLoginBindingModel userLoginBindingModel=new UserLoginBindingModel();
        userLoginBindingModel.setPassword(userEntity1.getPassword());
        userLoginBindingModel.setUsername(userEntity1.getPassword());
        String view=this.userController.loginConfirm(userLoginBindingModel,bindingResult,redirectAttributes);
    }

    @Test
    public void testLogout(){
        String view=this.userController.logout(httpSession);
        Assert.assertEquals("redirect:/",view);

    }
    @Test
    public void testInfo(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView=this.userController.info("admin",modelAndView,principal);
        Assert.assertEquals("user_info",modelAndView.getViewName());
    }

    @Test
    public void testProfile(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView=this.userController.profile("admin",modelAndView,principal);
        Assert.assertEquals("profile",modelAndView.getViewName());
    }

    @Test
    public void testAllPublications(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView=this.userController.publications("admin",modelAndView,principal);
        Assert.assertEquals("users-publications",modelAndView.getViewName());
    }

}
