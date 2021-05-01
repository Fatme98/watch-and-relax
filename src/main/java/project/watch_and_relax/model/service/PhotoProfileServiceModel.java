package project.watch_and_relax.model.service;

import project.watch_and_relax.model.entity.Photo;

public class PhotoProfileServiceModel {
    private Photo photo;

    public PhotoProfileServiceModel() {
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }
}
