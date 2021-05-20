package project.watch_and_relax.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import project.watch_and_relax.error.FilmNotFoundException;
import project.watch_and_relax.model.entity.*;
import project.watch_and_relax.model.service.*;
import project.watch_and_relax.model.view.*;
import project.watch_and_relax.repository.FilmRepository;
import project.watch_and_relax.service.*;

import javax.persistence.EntityExistsException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class FilmServiceImpl implements FilmService {
    private final FilmRepository filmRepository;
    private final ModelMapper modelMapper;
    private final CategoryService categoryService;
    private final PhotoService photoService;
    private final UserService userService;
    private final ActorService actorService;
    private final CommentService commentService;
    private final VideoService videoService;
    private final NewService newService;

    public FilmServiceImpl(FilmRepository filmRepository, ModelMapper modelMapper, CategoryService categoryService,
                           PhotoService photoService, UserService userService, ActorService actorService, CommentService commentService, VideoService videoService, NewService newService) {
        this.filmRepository = filmRepository;
        this.modelMapper = modelMapper;
        this.categoryService = categoryService;
        this.photoService = photoService;
        this.userService = userService;
        this.actorService = actorService;
        this.commentService = commentService;

        this.videoService = videoService;
        this.newService = newService;
    }

    @Override
    public void addFilmToUser(FilmServiceModel filmServiceModel, PhotoServiceModel photoServiceModel,String username) {
         Category category=this.categoryService.findByCategoryName(filmServiceModel.getCategory().getCategoryName());
         Film film = this.modelMapper.map(filmServiceModel, Film.class);
         film.setCategory(category);
         PhotoProfileServiceModel photoProfileServiceModel=this.photoService.
                                                               addPhoto(photoServiceModel);
         film.setPhoto(photoProfileServiceModel.getPhoto());
         UserViewModel userViewModel=this.userService.findUser(username);
         UserEntity userEntity=this.modelMapper.map(userViewModel,UserEntity.class);
         film.setUser(userEntity);
         Film filmCheck=this.filmRepository.findByName(filmServiceModel.getName()).orElse(null);
         if(filmCheck!=null){
             throw new EntityExistsException(String.format("Film with %s as name, already exists",
                                                                                         filmServiceModel.getName()));
         }
         this.filmRepository.saveAndFlush(film);
    }

    @Override
    public FilmServiceModel findById(String id) {
        Film film=this.filmRepository.findById(id).orElse(null);
        if(film==null){
            throw new FilmNotFoundException(id);
        }
        FilmServiceModel filmServiceModel=this.modelMapper.map(film,FilmServiceModel.class);
        filmServiceModel.setUsername(film.getUser().getUsername());
        filmServiceModel.setPhotoUrl(film.getPhoto().getImageUrl());
        return filmServiceModel;
    }

    @Override
    public FilmViewModel findByName(String name) {
        Film film=this.filmRepository.findByName(name).orElse(null);
        if(film==null){
            throw new FilmNotFoundException(name);
        }
        FilmViewModel filmViewModel=this.modelMapper.map(film,FilmViewModel.class);
        filmViewModel.setPhotoUrl(film.getPhoto().getImageUrl());
        return filmViewModel;
    }

    @Override
    public List<PhotoGalleryViewModel> getAllPicturesFromThatFilm(String id) {
        Film film=this.filmRepository.findById(id).orElse(null);
        if(film==null){
            throw new FilmNotFoundException(id);
        }
        List<PhotoGalleryViewModel>photos=new ArrayList<>();
        List<PhotoGalleryServiceModel> allPhotosFromThatFilmByName = this.photoService.findAllPhotosFromThatFilmByName(film.getName());
        for (PhotoGalleryServiceModel photoGalleryServiceModel : allPhotosFromThatFilmByName) {
            PhotoGalleryViewModel photoGalleryViewModel=new PhotoGalleryViewModel();
            photoGalleryViewModel.setId(photoGalleryServiceModel.getId());
            photoGalleryViewModel.setName(photoGalleryServiceModel.getName());
            photoGalleryViewModel.setPhotoUrl(photoGalleryServiceModel.getPhotoUrl());
            photos.add(photoGalleryViewModel);
        }

        return photos;
    }

    @Override
    public List<VideoViewModel> getAllVideosFromThatFilm(String id) {
        Film film = this.filmRepository.findById(id).orElse(null);
        if(film==null){
            throw new FilmNotFoundException(id);
        }
        List<VideoViewModel>videoViewModels=new ArrayList<>();
        for (VideoSetServiceModel video : this.videoService.findVideoByFilmName(film.getName())) {
            VideoViewModel videoViewModel=this.modelMapper.map(video,VideoViewModel.class);
            if(videoViewModel.getVideoUrl()!=null&&!videoViewModel.getVideoUrl().equals("")){
                videoViewModel.setVideoUrl(video.getVideoUrl());
                videoViewModels.add(videoViewModel);
            }
        }
        return videoViewModels;
    }

    @Override
    public List<YouTubeVideoViewModel> getAllYouTubeVideosFromThatFilm(String id) {
        Film film = this.filmRepository.findById(id).orElse(null);
        if(film==null){
            throw new FilmNotFoundException(id);
        }
        List<YouTubeVideoViewModel>youTubeVideoViewModels=new ArrayList<>();
        for (VideoSetServiceModel video : this.videoService.findVideoByFilmName(film.getName())) {
            if(!video.getYoutubeVideoUrl().equals("")){
                YouTubeVideoViewModel youTubeVideoViewModel=this.modelMapper.map(video,YouTubeVideoViewModel.class);
                youTubeVideoViewModel.setYouTubeVideo(video.getYoutubeVideoUrl());
                youTubeVideoViewModels.add(youTubeVideoViewModel);
            }

        }
        return youTubeVideoViewModels;
    }

    @Override
    public List<ActorListViewModel> getAllActorsFromThatFilm(String id) {
        Film film=this.filmRepository.findById(id).orElse(null);
        if(film==null){
            throw new FilmNotFoundException(id);
        }
        List<ActorListViewModel>actorListViewModels=new ArrayList<>();
        for (ActorViewModel actor : this.actorService.findAllActorByFilmName(film.getName())) {
            ActorListViewModel actorListViewModel=this.modelMapper.map(actor, ActorListViewModel.class);
            actorListViewModels.add(actorListViewModel);
        }
        return actorListViewModels;
    }




    @Override
    public List<CommentViewModel> getCommentsForFilm(String id) {
       Film film=this.filmRepository.findById(id).orElse(null);
        if(film==null){
            throw new FilmNotFoundException(id);
        }
        List<CommentViewModel>comments=new ArrayList<>();
        for (CommentServiceModel comment : this.commentService.getAllCommentsByFilmId(id)) {
            CommentViewModel commentViewModel=this.modelMapper.map(comment,CommentViewModel.class);
            commentViewModel.setUsersPhotoUrl(comment.getUserPhotoUrl());
            comments.add(commentViewModel);
        }
        return comments;
    }


    @Override
    public List<NewsListViewModel> getAllNewsTitles(String id) {
        Film film = this.filmRepository.findById(id).orElse(null);
        if(film==null){
            throw new FilmNotFoundException(id);
        }
        List<NewsListViewModel>news=new ArrayList<>();
        for (NewsServiceModel newsServiceModel : this.newService.findNewByFilmId(id)) {
           NewsListViewModel newsListViewModel=new NewsListViewModel();
           newsListViewModel.setTitle(newsServiceModel.getNewsTitle());
            newsListViewModel.setId(newsServiceModel.getId());
           news.add(newsListViewModel);
        }
        return news;
    }

    @Override
    public void deleteFilmById(String id) {
        Film film = this.filmRepository.findById(id).orElse(null);
        if (film == null) {
            throw new FilmNotFoundException("film not found");
        }
        this.photoService.deleteById(film.getPhoto().getId());
        this.userService.deleteFilmFromUser(film.getId(),film.getUser().getUsername());
        film.setPhoto(null);
        film.setUser(null);


        this.filmRepository.deleteFilmByName(film.getName());
    }

}
