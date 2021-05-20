package project.watch_and_relax.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.watch_and_relax.model.entity.UserEntity;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,String> {
    Optional<UserEntity> findByUsername(String username);
}
