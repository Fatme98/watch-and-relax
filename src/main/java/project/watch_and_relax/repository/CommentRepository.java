package project.watch_and_relax.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.watch_and_relax.model.entity.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,String> {
    List<Comment>findByFilmId(String id);
    List<Comment>findByActorId(String id);
}
