package project.watch_and_relax.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import project.watch_and_relax.model.service.PhotoGalleryServiceModel;
import project.watch_and_relax.model.service.PhotoProfileServiceModel;
import project.watch_and_relax.model.service.PhotoServiceModel;
import project.watch_and_relax.repository.PhotoRepository;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PhotoServiceTest {
    @Autowired
    private PhotoService photoService;
    @Autowired
    private PhotoRepository photoRepository;

    @Test
    public void testAddPhotoToWorkCorrectly(){
        PhotoServiceModel photoServiceModel=new PhotoServiceModel();
        photoServiceModel.setActorName("Actor");
        photoServiceModel.setFile(null);
        long dbSize=this.photoRepository.count();
        this.photoService.addPhoto(photoServiceModel);
        Assert.assertEquals(dbSize+1,this.photoRepository.count());
    }
    @Test
    public void testDeletePhotos(){
        PhotoServiceModel photoServiceModel=new PhotoServiceModel();
        photoServiceModel.setFilmName("Actor");
        photoServiceModel.setFile(null);
        this.photoService.addPhoto(photoServiceModel);
        long dbSize=this.photoRepository.count();
        this.photoService.deletePhotos("Actor");
        long dbSize1=this.photoRepository.count();
        Assert.assertEquals(dbSize-1,this.photoRepository.count());
    }
    @Test
    public void testDeleteById(){
        PhotoServiceModel photoServiceModel=new PhotoServiceModel();
        photoServiceModel.setFilmName("Actor");
        photoServiceModel.setFile(null);
        PhotoProfileServiceModel photoProfileServiceModel = this.photoService.addPhoto(photoServiceModel);
        long dbSize=this.photoRepository.count();
        this.photoService.deleteById(photoProfileServiceModel.getPhoto().getId());
        long dbSize1=this.photoRepository.count();
        Assert.assertEquals(dbSize-1,this.photoRepository.count());
    }
    @Test
    public void testFindAllPhotosFromThatFilmByName(){
        PhotoServiceModel photoServiceModel=new PhotoServiceModel();
        photoServiceModel.setFilmName("Film");
        photoServiceModel.setFile(null);
        PhotoProfileServiceModel photoProfileServiceModel = this.photoService.addPhoto(photoServiceModel);
        List<PhotoGalleryServiceModel> film =this.photoService.findAllPhotosFromThatFilmByName("Film");
        Assert.assertEquals(1,film.size());
    }
    @Test
    public void testFindAllPhotosFromThatActorByName(){
        PhotoServiceModel photoServiceModel=new PhotoServiceModel();
        photoServiceModel.setActorName("Actor");
        photoServiceModel.setFile(null);
        PhotoProfileServiceModel photoProfileServiceModel = this.photoService.addPhoto(photoServiceModel);
        List<PhotoGalleryServiceModel> actor =this.photoService.findAllPhotosFromThatActorByNickName("Actor");
        Assert.assertEquals(1,actor.size());
    }


}
