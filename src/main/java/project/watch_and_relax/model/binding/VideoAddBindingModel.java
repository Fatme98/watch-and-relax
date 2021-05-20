package project.watch_and_relax.model.binding;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

public class VideoAddBindingModel {
    private String actorName;
    private String filmName;
    private String videoName;
    private String youtubeVideoUrl;
    public VideoAddBindingModel() {
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public String getYoutubeVideoUrl() {
        return youtubeVideoUrl;
    }

    public void setYoutubeVideoUrl(String youtubeVideoUrl) {
        this.youtubeVideoUrl = youtubeVideoUrl;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }
}
