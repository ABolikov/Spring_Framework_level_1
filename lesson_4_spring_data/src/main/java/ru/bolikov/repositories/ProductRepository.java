package ru.bolikov.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bolikov.entity.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Page<Product> findByTitleLike(String titlePattern, Pageable pageable);

    Page<Product> findByTitleLikeAndCostGreaterThanEqual(String titlePattern, Integer minCost, Pageable pageable);

    Page<Product> findByTitleLikeAndCostLessThanEqual(String titlePattern, Integer maxCost, Pageable pageable);

    Page<Product> findByTitleLikeAndCostGreaterThanEqualAndCostLessThanEqual(String titlePattern, Integer minCost, Integer maxCost, Pageable pageable);

    Page<Product> findByCostLessThanEqual(Integer maxCost, Pageable pageable);

    Page<Product> findByCostGreaterThanEqual(Integer minCost, Pageable pageable);

    Page<Product> findByCostGreaterThanEqualAndCostLessThanEqual(Integer minCost, Integer maxCost, Pageable pageable);

}
