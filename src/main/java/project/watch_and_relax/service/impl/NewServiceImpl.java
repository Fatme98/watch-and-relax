package project.watch_and_relax.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import project.watch_and_relax.model.entity.New;
import project.watch_and_relax.model.service.NewsServiceModel;
import project.watch_and_relax.model.service.PhotoProfileServiceModel;
import project.watch_and_relax.model.service.PhotoServiceModel;
import project.watch_and_relax.model.view.NewsViewModel;
import project.watch_and_relax.repository.NewRepository;
import project.watch_and_relax.service.ActorService;
import project.watch_and_relax.service.FilmService;
import project.watch_and_relax.service.NewService;
import project.watch_and_relax.service.PhotoService;

import java.util.ArrayList;
import java.util.List;

@Service
public class NewServiceImpl implements NewService {
    private final NewRepository newRepository;
    private final ModelMapper modelMapper;
    private final PhotoService photoService;

    public NewServiceImpl(NewRepository newRepository, ModelMapper modelMapper, PhotoService photoService) {
        this.newRepository = newRepository;
        this.modelMapper = modelMapper;

        this.photoService = photoService;
    }

    @Override
    public void addNews(NewsServiceModel newsServiceModel, PhotoServiceModel photoServiceModel,String id) {
        PhotoProfileServiceModel photoProfileServiceModel = this.photoService.addPhoto(photoServiceModel);
        New news= this.modelMapper.map(newsServiceModel, New.class);
        if(newsServiceModel.getCategory().equals("ACTOR")){
           news.setActorId(id);
        }else if(newsServiceModel.getCategory().equals("FILM")){
           news.setFilmId(id);
        }
        news.setPhoto(photoProfileServiceModel.getPhoto());
        New news1=this.newRepository.saveAndFlush(news);
    }

    @Override
    public NewsViewModel findNewById(String id) {
        New news=this.newRepository.findNewById(id);
        NewsViewModel newsViewModel=this.modelMapper.map(news,NewsViewModel.class);
        newsViewModel.setPhotoUrl(news.getPhoto().getImageUrl());
        return newsViewModel;
    }

    @Override
    public List<NewsServiceModel> findNewByFilmId(String id) {
        List<New> byFilmId = this.newRepository.findByFilmId(id);
        List<NewsServiceModel>news=new ArrayList<>();
        for (New aNew : byFilmId) {
            NewsServiceModel newsServiceModel=new NewsServiceModel();
            newsServiceModel.setId(aNew.getId());
            newsServiceModel.setPhotoUrl(aNew.getPhoto().getImageUrl());
            newsServiceModel.setNewsBody(aNew.getNewsBody());
            newsServiceModel.setNewsTitle(aNew.getNewsTitle());
            news.add(newsServiceModel);
        }

        return news;
    }

    @Override
    public List<NewsServiceModel> findNewByActorId(String id) {
        List<New> byActorId = this.newRepository.findByActorId(id);
        List<NewsServiceModel>news=new ArrayList<>();
        for (New aNew : byActorId) {
            NewsServiceModel newsServiceModel=new NewsServiceModel();
            newsServiceModel.setId(aNew.getId());
            newsServiceModel.setPhotoUrl(aNew.getPhoto().getImageUrl());
            newsServiceModel.setNewsBody(aNew.getNewsBody());
            newsServiceModel.setNewsTitle(aNew.getNewsTitle());
            news.add(newsServiceModel);
        }

        return news;
    }

    @Override
    public void deleteNewById(String id) {
        New newById = this.newRepository.findNewById(id);
        String imageId=newById.getPhoto().getId();
        newById.setPhoto(null);
        this.newRepository.delete(newById);
        this.photoService.deleteById(imageId);
    }

    @Override
    public void deleteNewsForActorById(String id) {

    }
}
