package project.watch_and_relax.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import project.watch_and_relax.service.UserService;
import project.watch_and_relax.web.HomeController;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import java.security.Principal;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@Transactional
public class HomeControllerTest {
    @Autowired
    private HomeController homeController;
    @Autowired
    private HttpSession httpSession;
    @Mock
    Principal principal;
    @Test
    public void testIndexMethod(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView=this.homeController.index(modelAndView,httpSession);
        Assert.assertEquals("index",modelAndView.getViewName());
    }
    @Test(expected = Exception.class)
    public void testHomeMethod(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView=this.homeController.home(modelAndView,principal);
        Assert.assertEquals("home",modelAndView.getViewName());
    }

}
