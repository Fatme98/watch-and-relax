package project.watch_and_relax.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.watch_and_relax.model.entity.Category;
import project.watch_and_relax.model.entity.CategoryName;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,String> {

    Optional<Category> findByCategoryName(CategoryName category);
}
