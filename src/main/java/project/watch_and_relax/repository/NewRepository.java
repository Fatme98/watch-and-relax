package project.watch_and_relax.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.watch_and_relax.model.entity.New;

import java.util.List;


@Repository
public interface NewRepository  extends JpaRepository<New,String> {
    New findNewById(String id);
    List<New> findByFilmId(String id);
    List<New> findByActorId(String id);
}
