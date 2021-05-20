package project.watch_and_relax.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.watch_and_relax.model.entity.Announcement;

import java.time.Instant;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement,String> {
    void deleteByUpdatedOnBefore(Instant before);
}
