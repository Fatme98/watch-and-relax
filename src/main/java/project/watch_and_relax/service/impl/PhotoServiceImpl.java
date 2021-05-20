package project.watch_and_relax.service.impl;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import project.watch_and_relax.error.PhotoNotProvidedException;
import project.watch_and_relax.model.entity.Actor;
import project.watch_and_relax.model.entity.Film;
import project.watch_and_relax.model.entity.Photo;
import project.watch_and_relax.model.service.*;
import project.watch_and_relax.repository.PhotoRepository;
import project.watch_and_relax.service.FilmService;
import project.watch_and_relax.service.PhotoService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;

import javax.transaction.Transactional;

@Service
@Transactional
public class PhotoServiceImpl implements PhotoService {
    private final PhotoRepository photoRepository;
    private final ModelMapper modelMapper;
    public static final String UPLOAD_DIR="images";
    public PhotoServiceImpl(PhotoRepository photoRepository, ModelMapper modelMapper) {

        this.photoRepository = photoRepository;
        this.modelMapper = modelMapper;

    }

    @Override
    public PhotoProfileServiceModel addPhoto(PhotoServiceModel photoServiceModel) {
        Photo photo=new Photo();
        if(photoServiceModel.getFile()!=null){
            String name=photoServiceModel.getFile().getOriginalFilename();
            long size=photoServiceModel.getFile().getSize();
            try{
                File currentDir=new File(UPLOAD_DIR);
                if(!currentDir.exists()){
                    currentDir.mkdirs();
                }
                String path=currentDir.getAbsolutePath()+"/"+photoServiceModel.getFile().getOriginalFilename();
                path=new File(path).getAbsolutePath();
                File f=new File(path);
                FileCopyUtils.copy(photoServiceModel.getFile().getInputStream(),new FileOutputStream(f));

            } catch (IOException e) {
                e.printStackTrace();
            }
            String path1="/"+UPLOAD_DIR+"/"+photoServiceModel.getFile().getOriginalFilename();
            photo.setImageUrl(path1);
        }
        
        PhotoProfileServiceModel photoProfileServiceModel=new PhotoProfileServiceModel();
        photo.setActorNickname(photoServiceModel.getActorName());
        photo.setFilmName(photoServiceModel.getFilmName());
        photo.setActorId(photoServiceModel.getActorId());
        photo.setFilmId(photoServiceModel.getFilmId());
        photo=this.photoRepository.saveAndFlush(photo);
        photoProfileServiceModel.setPhoto(photo);
        return photoProfileServiceModel;
    }

    @Override
    public void deletePhotos(String name) {
        List<Photo> byFilmName = this.photoRepository.findByFilmName(name);
        for (Photo photo : byFilmName) {
            this.photoRepository.delete(photo);
        }
    }

    @Override
    public void deleteById(String id) {
        this.photoRepository.deleteById(id);
    }

    @Override
    public Photo findByUrl(String photoUrl) {
        Photo photo=this.photoRepository.findByImageUrl(photoUrl);
        return photo;
    }

    @Override
    public List<PhotoGalleryServiceModel> findAllPhotosFromThatFilmByName(String name) {
        List<Photo> byFilmName = this.photoRepository.findByFilmName(name);
        List<PhotoGalleryServiceModel>photoGalleryServiceModels=new ArrayList<>();
        for (Photo photo : byFilmName) {
            PhotoGalleryServiceModel photoGalleryServiceModel=new PhotoGalleryServiceModel();
            photoGalleryServiceModel.setId(photo.getId());
            photoGalleryServiceModel.setPhotoUrl(photo.getImageUrl());
            photoGalleryServiceModels.add(photoGalleryServiceModel);
        }
        return photoGalleryServiceModels;
    }

    @Override
    public List<PhotoGalleryServiceModel> findAllPhotosFromThatActorByNickName(String name) {
        List<Photo> byNickName = this.photoRepository.findByActorNickname(name);
        List<PhotoGalleryServiceModel>photoGalleryServiceModels=new ArrayList<>();
        for (Photo photo : byNickName) {
            PhotoGalleryServiceModel photoGalleryServiceModel=new PhotoGalleryServiceModel();
            photoGalleryServiceModel.setId(photo.getId());
            photoGalleryServiceModel.setPhotoUrl(photo.getImageUrl());
            photoGalleryServiceModels.add(photoGalleryServiceModel);
        }
        return photoGalleryServiceModels;
    }

}
