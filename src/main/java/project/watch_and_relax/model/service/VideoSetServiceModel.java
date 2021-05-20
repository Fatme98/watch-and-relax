package project.watch_and_relax.model.service;

import org.springframework.web.multipart.MultipartFile;

public class VideoSetServiceModel {
    private String id;
    private String actorName;
    private String filmName;
    private String videoName;
    private String youtubeVideoUrl;
   private String videoUrl;

    public VideoSetServiceModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getYoutubeVideoUrl() {
        return youtubeVideoUrl;
    }

    public void setYoutubeVideoUrl(String youtubeVideoUrl) {
        this.youtubeVideoUrl = youtubeVideoUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }
}
