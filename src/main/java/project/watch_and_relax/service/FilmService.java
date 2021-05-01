package project.watch_and_relax.service;

import project.watch_and_relax.model.entity.Film;
import project.watch_and_relax.model.entity.Photo;
import project.watch_and_relax.model.entity.Video;
import project.watch_and_relax.model.service.*;
import project.watch_and_relax.model.view.*;

import java.util.List;

public interface FilmService {
    void addFilmToUser(FilmServiceModel filmServiceModel, PhotoServiceModel photoServiceModel, String username);
    FilmServiceModel findById(String id);
    FilmViewModel findByName(String name);
    List<PhotoGalleryViewModel>getAllPicturesFromThatFilm(String id);
    List<VideoViewModel>getAllVideosFromThatFilm(String id);
    List<YouTubeVideoViewModel>getAllYouTubeVideosFromThatFilm(String id);
    List<ActorListViewModel>getAllActorsFromThatFilm(String id);
    List<CommentViewModel> getCommentsForFilm(String id);
    List<NewsListViewModel>getAllNewsTitles(String id);
    void deleteFilmById(String id);

}
