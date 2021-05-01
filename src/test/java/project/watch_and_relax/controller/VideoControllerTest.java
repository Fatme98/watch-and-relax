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
import project.watch_and_relax.web.PhotoController;
import project.watch_and_relax.web.VideoController;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.security.Principal;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@Transactional
public class VideoControllerTest {
    @Autowired
    private VideoController videoController;
    @Autowired
    private HttpSession httpSession;
    @Mock
    private Principal principal;
    @Mock
    private Model model;
    @Test
    public void testAddVideo(){
       String view= this.videoController.add("id",model,principal);
        Assert.assertEquals("add_video",view);
    }
}
