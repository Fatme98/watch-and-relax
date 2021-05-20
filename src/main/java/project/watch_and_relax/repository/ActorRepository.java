package project.watch_and_relax.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.watch_and_relax.model.entity.Actor;

import java.lang.annotation.Native;
import java.util.List;
import java.util.Optional;

import static org.hibernate.hql.internal.antlr.SqlTokenTypes.FROM;
import static org.hibernate.hql.internal.antlr.SqlTokenTypes.WHERE;
import static org.hibernate.loader.Loader.SELECT;

@Repository
public interface ActorRepository extends JpaRepository<Actor,String> {
    Optional<Actor>findByFullName(String fullName);
    Optional<Actor>findByNickName(String nickName);
    Optional<Actor> findById(String id);
    List<Actor>findByFilmName(String name);


}
