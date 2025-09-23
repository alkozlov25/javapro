package edu.t1.repository;

import edu.t1.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    List<ProductEntity> findProductEntitiesByUserId(Integer userId);
}
