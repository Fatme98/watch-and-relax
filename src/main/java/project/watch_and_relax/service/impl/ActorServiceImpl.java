package project.watch_and_relax.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.watch_and_relax.error.ActorNotFoundException;
import project.watch_and_relax.model.entity.*;
import project.watch_and_relax.model.service.*;
import project.watch_and_relax.model.view.*;
import project.watch_and_relax.repository.ActorRepository;
import project.watch_and_relax.service.*;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class ActorServiceImpl implements ActorService {
    private final ModelMapper modelMapper;
    private final ActorRepository actorRepository;
    private final PhotoService photoService;
    private final VideoService videoService;
    private final CommentService commentService;
    private final NewService newService;
    @Autowired
    public ActorServiceImpl(ModelMapper modelMapper, ActorRepository actorRepository,
                            PhotoService photoService, VideoService videoService, CommentService commentService, NewService newService) {
        this.modelMapper = modelMapper;
        this.actorRepository = actorRepository;
        this.photoService = photoService;

        this.videoService = videoService;

        this.commentService = commentService;
        this.newService = newService;
    }

    @Override
    public void addActor(ActorServiceModel actorServiceModel, PhotoServiceModel photoServiceModel,
                         FilmServiceModel filmServiceModel) {
        PhotoProfileServiceModel photoProfileServiceModel = this.photoService.addPhoto(photoServiceModel);
        Actor actor = this.modelMapper.map(actorServiceModel, Actor.class);
        actor.setPhoto(photoProfileServiceModel.getPhoto());
        Film film = this.modelMapper.map(filmServiceModel, Film.class);
        actor.setFilm(film);
        this.actorRepository.saveAndFlush(actor);
    }


    @Override
    public ActorServiceModel findById(String id) {
        Actor actor = this.actorRepository.findById(id).orElse(null);
        if (actor == null) {
             throw new ActorNotFoundException(id);
        }
        String username=actor.getFilm().getUser().getUsername();
        ActorServiceModel actorServiceModel = this.modelMapper.map(actor,ActorServiceModel.class);
        actorServiceModel.setPhotoUrl(actor.getPhoto().getImageUrl());
        actorServiceModel.setFilmId(actor.getFilm().getId());
        actorServiceModel.setUserId(actor.getFilm().getUser().getId());
        actorServiceModel.setUsername(username);
        actorServiceModel.setRoleName(actor.getRoleName());
        return actorServiceModel;
    }

    @Override
    public ActorViewModel findActorByName(String name) {
        Actor actor = this.actorRepository.findByNickName(name).orElse(null);
        if (actor == null) {
            throw new ActorNotFoundException(name);
        }
        ActorViewModel actorViewModel = this.modelMapper.map(actor,ActorViewModel.class);
        actorViewModel.setPhotoUrl(actor.getPhoto().getImageUrl());
        actorViewModel.setFilmId(actor.getFilm().getId());
        actorViewModel.setUserId(actor.getFilm().getUser().getId());
        return actorViewModel;
    }

    @Override
    public List<ActorViewModel> findAllActorByFilmName(String name) {
        List<Actor> byFilmName = this.actorRepository.findByFilmName(name);
        List<ActorViewModel>actors=new ArrayList<>();
        for (Actor actor : byFilmName) {
            ActorViewModel actorViewModel=this.modelMapper.map(actor, ActorViewModel.class);
            actorViewModel.setPhotoUrl(actor.getPhoto().getImageUrl());
            actorViewModel.setFilmId(actor.getFilm().getId());
            actorViewModel.setUserId(actor.getFilm().getUser().getId());
            actors.add(actorViewModel);
        }

        return actors;
    }


    @Override
    public List<PhotoGalleryViewModel> getPhotosFromThatActor(String id) {
        Actor actor = this.actorRepository.findById(id).orElse(null);
        if (actor == null) {
            throw  new ActorNotFoundException(id);
        }
        List<PhotoGalleryViewModel>photos=new ArrayList<>();
        List<PhotoGalleryServiceModel> allPhotosFromThatActorByNickName = this.photoService.findAllPhotosFromThatActorByNickName(actor.getNickName());
        for (PhotoGalleryServiceModel photoGalleryServiceModel : allPhotosFromThatActorByNickName) {
            PhotoGalleryViewModel photoGalleryViewModel=new PhotoGalleryViewModel();
            photoGalleryViewModel.setId(photoGalleryServiceModel.getId());
            photoGalleryViewModel.setName(photoGalleryServiceModel.getName());
            photoGalleryViewModel.setPhotoUrl(photoGalleryServiceModel.getPhotoUrl());
            photos.add(photoGalleryViewModel);
        }
        return photos;
    }

    @Override
    public List<VideoViewModel> getAllVideosFromThatActor(String id) {
        Actor actor = this.actorRepository.findById(id).orElse(null);
        if (actor == null) {
            throw  new ActorNotFoundException(id);
        }
        List<VideoViewModel>videoViewModels=new ArrayList<>();
        for (VideoSetServiceModel video : this.videoService.findVideoByActorNickName(actor.getNickName())) {
            VideoViewModel videoViewModel=this.modelMapper.map(video,VideoViewModel.class);
            if(videoViewModel.getVideoUrl()!=null&&!videoViewModel.getVideoUrl().equals("")){
                videoViewModel.setVideoUrl(video.getVideoUrl());
                videoViewModels.add(videoViewModel);
            }
        }
        return videoViewModels;
    }

    @Override
    public List<YouTubeVideoViewModel> getAllYouTubeVideosFromThatActors(String id) {
        Actor actor = this.actorRepository.findById(id).orElse(null);
        if (actor == null) {
            throw  new ActorNotFoundException(id);
        }
        List<YouTubeVideoViewModel>youTubeVideoViewModels=new ArrayList<>();
        for (VideoSetServiceModel video : this.videoService.findVideoByActorNickName(actor.getNickName())) {
            if(!video.getYoutubeVideoUrl().equals("")){
                YouTubeVideoViewModel youTubeVideoViewModel=this.modelMapper.map(video,YouTubeVideoViewModel.class);
                youTubeVideoViewModel.setYouTubeVideo(video.getYoutubeVideoUrl());
                youTubeVideoViewModel.setVideoName(video.getVideoName());
                youTubeVideoViewModels.add(youTubeVideoViewModel);
            }

        }
        return youTubeVideoViewModels;
    }





    @Override
    public List<CommentViewModel> getCommentsForActor(String id) {
        Actor actor=this.actorRepository.findById(id).orElse(null);
        if(actor==null){
            throw  new ActorNotFoundException(id);
        }
        List<CommentViewModel>comments=new ArrayList<>();
        for (CommentServiceModel comment : this.commentService.getAllCommentsByActorId(id)) {
            CommentViewModel commentViewModel=this.modelMapper.map(comment,CommentViewModel.class);
            commentViewModel.setUsersPhotoUrl(comment.getUserPhotoUrl());
            comments.add(commentViewModel);
        }
        return comments;

    }



    @Override
    public List<NewsListViewModel> getAllNewsTitles(String id) {

        Actor actor = this.actorRepository.findById(id).orElse(null);
        if(actor==null){
            throw new ActorNotFoundException(id);
        }
        List<NewsListViewModel>news=new ArrayList<>();
        for (NewsServiceModel newInTheLoop : this.newService.findNewByActorId(actor.getId())) {
            NewsListViewModel newsListViewModel=new NewsListViewModel();
            newsListViewModel.setTitle(newInTheLoop.getNewsTitle());
            newsListViewModel.setId(newInTheLoop.getId());
            news.add(newsListViewModel);
        }
        return news;
    }

    @Override
    public void deleteActorById(String id) {
       Actor actor= this.actorRepository.findById(id).orElse(null);
       if(actor!=null){
           this.photoService.deleteById(actor.getPhoto().getId());
           actor.setPhoto(null);
           actor.setFilm(null);
           this.actorRepository.deleteById(actor.getId());
       }

    }
}

