package project.watch_and_relax.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.watch_and_relax.model.entity.Video;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video,String> {
    List<Video> findAllByActorNickname(String actorNickName);
    List<Video> findAllByFilmName(String filmName);
}
