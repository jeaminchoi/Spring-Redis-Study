package sparta.study.api.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sparta.study.api.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
