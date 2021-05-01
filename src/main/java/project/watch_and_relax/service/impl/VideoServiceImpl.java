package project.watch_and_relax.service.impl;

import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import project.watch_and_relax.error.VideoNotProvidedException;
import project.watch_and_relax.model.entity.Photo;
import project.watch_and_relax.model.entity.Video;
import project.watch_and_relax.model.service.PhotoGalleryServiceModel;
import project.watch_and_relax.model.service.VideoServiceModel;
import project.watch_and_relax.model.service.VideoSetServiceModel;
import project.watch_and_relax.model.view.VideoViewModel;
import project.watch_and_relax.repository.VideoRepository;
import project.watch_and_relax.service.VideoService;

import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class VideoServiceImpl implements VideoService {
    private final VideoRepository videoRepository;
    public static final String UPLOAD_DIR="videos";
@Autowired
    public VideoServiceImpl(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    @Override
    public VideoSetServiceModel addVideo(VideoServiceModel videoServiceModel,String id) {
        Video video=new Video();
        video.setFilmName(videoServiceModel.getFilmName());
        video.setActorNickname(videoServiceModel.getActorName());
        video.setVideoName(videoServiceModel.getVideoName());
        video.setActorId(videoServiceModel.getActorId());
        video.setFilmId(videoServiceModel.getFilmId());
        VideoSetServiceModel videoSetServiceModel=new VideoSetServiceModel();
        videoSetServiceModel.setActorName(video.getActorNickname());
        videoSetServiceModel.setFilmName(video.getFilmName());
        videoSetServiceModel.setVideoName(video.getVideoName());
        if(videoServiceModel.getYoutubeVideoUrl().equals("")){
            handleMultipartFile(video, videoServiceModel.getMultipartFile());
            video=this.videoRepository.saveAndFlush(video);
            videoSetServiceModel.setYoutubeVideoUrl("");
            videoSetServiceModel.setVideoUrl(video.getVideoUrl());
            videoSetServiceModel.setId(video.getId());
        }else {
            setYouTubeVideo(video,videoServiceModel.getYoutubeVideoUrl());
            video=this.videoRepository.saveAndFlush(video);
            videoSetServiceModel.setYoutubeVideoUrl(video.getYoutubeVideoUrl());
            videoSetServiceModel.setVideoUrl("");
            videoSetServiceModel.setId(video.getId());
        }

        return videoSetServiceModel;
    }

    @Override
    public List<VideoSetServiceModel> findVideoByFilmName(String name) {
        List<Video> allByFilmName = this.videoRepository.findAllByFilmName(name);
        List<VideoSetServiceModel>videos=new ArrayList<>();
        for (Video video : allByFilmName) {
            VideoSetServiceModel videoSetServiceModel=new VideoSetServiceModel();
            videoSetServiceModel.setFilmName(video.getFilmName());
            videoSetServiceModel.setVideoName(video.getVideoName());
            videoSetServiceModel.setVideoUrl(video.getVideoUrl());
            videoSetServiceModel.setYoutubeVideoUrl(video.getYoutubeVideoUrl());
            videoSetServiceModel.setId(video.getId());
            videos.add(videoSetServiceModel);
        }

        return videos;
    }

    @Override
    public List<VideoSetServiceModel> findVideoByActorNickName(String name) {
        List<Video> allByActorNickname = this.videoRepository.findAllByActorNickname(name);
        List<VideoSetServiceModel>videos=new ArrayList<>();
        for (Video video : allByActorNickname) {
            VideoSetServiceModel videoSetServiceModel=new VideoSetServiceModel();
            videoSetServiceModel.setFilmName(video.getFilmName());
            videoSetServiceModel.setVideoName(video.getVideoName());
            videoSetServiceModel.setVideoUrl(video.getVideoUrl());
            videoSetServiceModel.setYoutubeVideoUrl(video.getYoutubeVideoUrl());
            videoSetServiceModel.setId(video.getId());
            videos.add(videoSetServiceModel);
        }

        return videos;
    }

    @Override
    public void deleteById(String id) {
        this.videoRepository.deleteById(id);
    }

    private void setYouTubeVideo(Video video,String youtubeFile){
        if(youtubeFile.length()>32){
            if(youtubeFile.contains("&")){
                int index=youtubeFile.indexOf("&");
                String url="https://www.youtube.com/embed/"+youtubeFile.substring(32,index);
                video.setYoutubeVideoUrl(url);
            }else{
                String url="https://www.youtube.com/embed/"+youtubeFile.substring(32);
                video.setYoutubeVideoUrl(url);
            }
            video.setVideoUrl("");
        }
    }
    private void handleMultipartFile(Video video, MultipartFile fileInput){

        String name=fileInput.getOriginalFilename();
        long size=fileInput.getSize();
        try{
            File currentDir=new File(UPLOAD_DIR);
            if(!currentDir.exists()){
                currentDir.mkdirs();
            }
            String path=currentDir.getAbsolutePath()+"/"+fileInput.getOriginalFilename();
            path=new File(path).getAbsolutePath();
            File f=new File(path);
            FileCopyUtils.copy(fileInput.getInputStream(),new FileOutputStream(f));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String url="/"+UPLOAD_DIR+"/"+fileInput.getOriginalFilename();
        video.setVideoUrl(url);
        video.setYoutubeVideoUrl("");
    }



}
