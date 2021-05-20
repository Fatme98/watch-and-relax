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
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.watch_and_relax.model.binding.PhotoAddBindingModel;
import project.watch_and_relax.web.PhotoController;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.security.Principal;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@Transactional
public class PhotoControllerTest {
    @Autowired
    private PhotoController photoController;
    @Autowired
    private HttpSession httpSession;
    @Mock
    private Principal principal;
    @Mock
    private Model model;

    @Test(expected = Exception.class)
    public void testAddMethod(){
        String view=this.photoController.add("id",model,principal);
        Assert.assertEquals("add_photo",view);
    }
}
