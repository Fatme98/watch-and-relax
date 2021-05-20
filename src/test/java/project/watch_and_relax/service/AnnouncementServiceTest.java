package project.watch_and_relax.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import project.watch_and_relax.model.binding.AnnouncementAddBindingModel;
import project.watch_and_relax.model.entity.Announcement;
import project.watch_and_relax.repository.AnnouncementRepository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AnnouncementServiceTest {
    @Autowired
    private AnnouncementService announcementService;
    @Autowired
    AnnouncementRepository announcementRepository;
    @Test
    public void testFindAll(){
        long dbSize=this.announcementRepository.count();
        Announcement announcement=new Announcement();
        announcement.setCreatedOn(Instant.now());
        announcement.setUpdatedOn(Instant.now());
        announcement.setDescription("sdjfdkfghfgkfghfgkf");
        announcement.setTitle("dfjdiggfiggwo");
        this.announcementRepository.save(announcement);
        Assertions.assertEquals(dbSize+1,this.announcementService.findAll().size());
    }
    @Test
    public void testCreateOrUpdate(){
        long dbSize=this.announcementRepository.count();
        AnnouncementAddBindingModel announcement=new AnnouncementAddBindingModel();
        announcement.setCreatedOn(Instant.now());
        announcement.setUpdatedOn(Instant.now());
        announcement.setDescription("sdjfdkfghfgkfghfgkf");
        announcement.setTitle("dfjdiggfiggwo");
        this.announcementService.createOrUpdateAnnouncement(announcement);
        Assert.assertEquals(dbSize+1,this.announcementRepository.count());
    }
    @Test
    public void testDelete(){
        this.announcementService.deleteAll();
        Assert.assertEquals(0,this.announcementRepository.count());
    }

}
