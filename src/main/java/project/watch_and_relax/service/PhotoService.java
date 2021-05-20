package project.watch_and_relax.service;

import project.watch_and_relax.model.entity.Actor;
import project.watch_and_relax.model.entity.Film;
import project.watch_and_relax.model.entity.Photo;
import project.watch_and_relax.model.service.PhotoGalleryServiceModel;
import project.watch_and_relax.model.service.PhotoProfileServiceModel;
import project.watch_and_relax.model.service.PhotoServiceModel;

import java.awt.image.BufferedImage;
import java.util.List;

public interface PhotoService {
    PhotoProfileServiceModel addPhoto(PhotoServiceModel photoServiceModel);

    void deletePhotos(String name);

    void deleteById(String id);

    Photo findByUrl(String photoUrl);
    List<PhotoGalleryServiceModel>findAllPhotosFromThatFilmByName(String name);
    List<PhotoGalleryServiceModel>findAllPhotosFromThatActorByNickName(String name);
}
