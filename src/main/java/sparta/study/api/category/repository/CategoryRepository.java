package sparta.study.api.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sparta.study.api.category.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
