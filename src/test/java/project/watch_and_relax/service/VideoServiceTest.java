package project.watch_and_relax.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import project.watch_and_relax.model.entity.Video;
import project.watch_and_relax.model.service.VideoServiceModel;
import project.watch_and_relax.model.service.VideoSetServiceModel;
import project.watch_and_relax.repository.VideoRepository;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class VideoServiceTest {
    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private VideoService videoService;
    @Test
    public void testAddVideoToWorkCorrectly(){
        VideoServiceModel videoServiceModel=new VideoServiceModel();
        videoServiceModel.setYoutubeVideoUrl("https://www.youtube.com/watch?v=0FMarSJX8Q8");
        long dbSize=this.videoRepository.count();
        this.videoService.addVideo(videoServiceModel,null);
        Assert.assertEquals(dbSize+1,this.videoRepository.count());
    }
    @Test
    public void testAddVideoToWorkCorrectlyWithPlayList(){
        VideoServiceModel videoServiceModel=new VideoServiceModel();
        videoServiceModel.setYoutubeVideoUrl("https://www.youtube.com/watch?v=dVD9EJk-XHA&list=PLkaaLhW-ngnxXpf3fMpDs6LuF165dq8kv");
        long dbSize=this.videoRepository.count();
        this.videoService.addVideo(videoServiceModel,null);
        Assert.assertEquals(dbSize+1,this.videoRepository.count());
    }

    @Test
    public void testFindVideoByFilmName(){
        VideoServiceModel videoServiceModel=new VideoServiceModel();
        videoServiceModel.setYoutubeVideoUrl("https://www.youtube.com/watch?v=dVD9EJk-XHA&list=PLkaaLhW-ngnxXpf3fMpDs6LuF165dq8kv");
        videoServiceModel.setFilmName("LoveForRent");
        this.videoService.addVideo(videoServiceModel,null);
        List<VideoSetServiceModel>videos=this.videoService.findVideoByFilmName("LoveForRent");
        for (VideoSetServiceModel video : videos) {
            //Assert.assertEquals("LoveForRent",video.getFilmName());
        }
    }
    @Test
    public void testFindVideoByActorName(){
        VideoServiceModel videoServiceModel=new VideoServiceModel();
        videoServiceModel.setYoutubeVideoUrl("https://www.youtube.com/watch?v=dVD9EJk-XHA&list=PLkaaLhW-ngnxXpf3fMpDs6LuF165dq8kv");
        videoServiceModel.setFilmName("Elcin");
        this.videoService.addVideo(videoServiceModel,null);
        List<VideoSetServiceModel>videos=this.videoService.findVideoByFilmName("Elcin");
        for (VideoSetServiceModel video : videos) {
           // Assert.assertEquals("Elcin",video.getFilmName());
        }
    }
    @Test
    public void testDeleteById(){
        VideoServiceModel videoServiceModel=new VideoServiceModel();
        videoServiceModel.setYoutubeVideoUrl("https://www.youtube.com/watch?v=dVD9EJk-XHA&list=PLkaaLhW-ngnxXpf3fMpDs6LuF165dq8kv");
        videoServiceModel.setFilmName("Elcin");
        VideoSetServiceModel videoTest=this.videoService.addVideo(videoServiceModel,null);
        long dbSize=this.videoRepository.count();
        this.videoService.deleteById(videoTest.getId());
        Assert.assertEquals(dbSize-1,this.videoRepository.count());
    }


}
