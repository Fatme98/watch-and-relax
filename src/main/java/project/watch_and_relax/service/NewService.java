package project.watch_and_relax.service;

import project.watch_and_relax.model.service.NewsServiceModel;
import project.watch_and_relax.model.service.PhotoServiceModel;
import project.watch_and_relax.model.view.NewsViewModel;

import java.util.List;

public interface NewService {
    void addNews(NewsServiceModel newsServiceModel, PhotoServiceModel photoServiceModel, String id);

    NewsViewModel findNewById(String id);
    List<NewsServiceModel>findNewByFilmId(String id);
    List<NewsServiceModel>findNewByActorId(String id);
    void deleteNewById(String id);

    void deleteNewsForActorById(String id);
}
