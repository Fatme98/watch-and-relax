package project.watch_and_relax;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import project.watch_and_relax.model.binding.AnnouncementAddBindingModel;
import project.watch_and_relax.service.AnnouncementService;

import javax.transaction.Transactional;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AnnouncementCleanupSchedulerTest {
    @Autowired
    private AnnouncementService announcementService;
    @Test
    public void testCleanupOldAnnouncements(){
        long size=this.announcementService.findAll().size();
        this.announcementService.cleanUpOldAnnouncements();
        Assert.assertEquals(size,this.announcementService.findAll().size());
    }
}
