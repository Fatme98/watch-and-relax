package project.watch_and_relax.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.watch_and_relax.model.entity.Actor;
import project.watch_and_relax.model.entity.Film;

import java.util.List;
import java.util.Optional;

@Repository
public interface FilmRepository extends JpaRepository<Film,String> {
     Optional<Film> findByName(String name);
     void deleteFilmByName(String name);
}
