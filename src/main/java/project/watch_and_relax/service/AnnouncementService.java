package project.watch_and_relax.service;

import project.watch_and_relax.model.binding.AnnouncementAddBindingModel;

import java.util.List;

public interface AnnouncementService {
    List<AnnouncementAddBindingModel>findAll();
    void cleanUpOldAnnouncements();
    void createOrUpdateAnnouncement(AnnouncementAddBindingModel announcementAddBindingModel);
    void deleteAll();
}
