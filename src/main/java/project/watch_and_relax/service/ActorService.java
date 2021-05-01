package project.watch_and_relax.service;

import project.watch_and_relax.model.entity.Photo;
import project.watch_and_relax.model.entity.Video;
import project.watch_and_relax.model.service.*;
import project.watch_and_relax.model.view.*;

import java.util.List;

public interface ActorService {
    void addActor(ActorServiceModel actorServiceModel, PhotoServiceModel photoServiceModel,FilmServiceModel filmServiceModel);
    ActorServiceModel findById(String id);
    ActorViewModel findActorByName(String name);
    List<ActorViewModel>findAllActorByFilmName(String name);
    List<PhotoGalleryViewModel>getPhotosFromThatActor(String id);
    List<VideoViewModel>getAllVideosFromThatActor(String id);
    List<YouTubeVideoViewModel>getAllYouTubeVideosFromThatActors(String id);
    List<CommentViewModel>getCommentsForActor(String id);
    List<NewsListViewModel> getAllNewsTitles(String id);
    void deleteActorById(String id);
}
