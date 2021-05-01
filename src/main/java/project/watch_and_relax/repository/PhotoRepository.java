package project.watch_and_relax.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.watch_and_relax.model.entity.Photo;
import project.watch_and_relax.model.service.PhotoGalleryServiceModel;

import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<Photo,String> {
    List<Photo>findByActorNickname(String actorNickName);
    List<Photo>findByFilmName(String filmName);
    Photo findByImageUrl(String imageUrl);
}
