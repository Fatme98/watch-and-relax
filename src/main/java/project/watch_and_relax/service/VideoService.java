package project.watch_and_relax.service;

import project.watch_and_relax.model.service.VideoServiceModel;
import project.watch_and_relax.model.service.VideoSetServiceModel;
import project.watch_and_relax.model.view.VideoViewModel;

import java.util.List;

public interface VideoService {
    VideoSetServiceModel addVideo(VideoServiceModel videoServiceModel,String id);
    List<VideoSetServiceModel>findVideoByFilmName(String name);
    List<VideoSetServiceModel>findVideoByActorNickName(String name);
    void deleteById(String id);
}
