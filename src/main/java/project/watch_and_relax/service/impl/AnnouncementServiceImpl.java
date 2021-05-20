package project.watch_and_relax.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.watch_and_relax.mapper.AnnouncementMapper;
import project.watch_and_relax.model.binding.AnnouncementAddBindingModel;
import project.watch_and_relax.model.entity.Announcement;
import project.watch_and_relax.repository.AnnouncementRepository;
import project.watch_and_relax.service.AnnouncementService;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {
    private final AnnouncementRepository announcementRepository;
@Autowired
    public AnnouncementServiceImpl(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    @Override
    public List<AnnouncementAddBindingModel> findAll() {
        return this.announcementRepository.findAll().stream()
                .map(AnnouncementMapper.INSTANCE::mapAnnouncementToBindingModel).collect(Collectors.toList());
    }

    @Override
    public void cleanUpOldAnnouncements() {
          Instant endTime= Instant.now().minus(10, ChronoUnit.DAYS);
          announcementRepository.deleteByUpdatedOnBefore(endTime);
    }

    @Override
    public void createOrUpdateAnnouncement(AnnouncementAddBindingModel announcementAddBindingModel) {
        Announcement announcement=AnnouncementMapper.INSTANCE.
                mapAnnouncementBindingToEntity(announcementAddBindingModel);
        if(announcement.getCreatedOn()==null){
            announcement.setCreatedOn(Instant.now());
        }
        announcement.setUpdatedOn(Instant.now());
        announcementRepository.save(announcement);
    }

    @Override
    public void deleteAll() {
         announcementRepository.deleteAll();
    }
}
