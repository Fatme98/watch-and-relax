package project.watch_and_relax.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.watch_and_relax.error.UserNotFoundException;
import project.watch_and_relax.model.binding.AnnouncementAddBindingModel;
import project.watch_and_relax.model.binding.CommentAddBindingModel;
import project.watch_and_relax.model.entity.Announcement;
import project.watch_and_relax.repository.AnnouncementRepository;
import project.watch_and_relax.service.AnnouncementService;
import project.watch_and_relax.web.AnnouncementController;

import javax.transaction.Transactional;
import java.security.Principal;
import java.time.Instant;
import java.util.List;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@Transactional
public class AnnouncementControllerTest {
    @Autowired
    private AnnouncementController announcementController;
    @Mock
    private Model model;
    @Mock
    private Principal principal;
    @Mock
    private RedirectAttributes redirectAttributes;
    @Mock
    private BindingResult bindingResult;
    @Autowired
    private AnnouncementRepository announcementRepository;
    @Test
    public void testFindAll(){
        String view=this.announcementController.announcement(model,"admin");
        Assert.assertEquals("announcements",view);
    }
    @Test
    public void testNewToWork(){
        String view=this.announcementController.newAnnouncement(model,"admin");
        Assert.assertEquals("new",view);
    }
    @Test(expected = UserNotFoundException.class)
    public void testNewToForbidAccess(){
        String view=this.announcementController.newAnnouncement(model,"pesho");
        Assert.assertEquals("redirect:/home",view);
    }

    @Test(expected = UserNotFoundException.class)
    public void testSaveToForbidAccess(){
        AnnouncementAddBindingModel announcementAddBindingModel=new AnnouncementAddBindingModel();
        announcementAddBindingModel.setTitle("dkjdhwfjlv");
        announcementAddBindingModel.setDescription("djfhekfjfkfhgfgjhfgjfgfghjgflw");
        String view=this.announcementController.save(announcementAddBindingModel,bindingResult,redirectAttributes,"pesho");
        Assert.assertEquals("redirect:/home",view);
    }
    @Test
    public void testDelete(){
        Announcement announcement=new Announcement();
        announcement.setTitle("dkjdhwfjlv");
        announcement.setDescription("djfhekfjfkfhgfgjhfgjfgfghjgflw");
        announcement.setUpdatedOn(Instant.now());
        announcement.setCreatedOn(Instant.now());
        this.announcementRepository.save(announcement);
        this.announcementController.delete("admin");
        Assert.assertEquals(0,this.announcementRepository.count());
    }

}
