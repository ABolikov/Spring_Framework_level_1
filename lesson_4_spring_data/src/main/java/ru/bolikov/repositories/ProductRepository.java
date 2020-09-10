package ru.bolikov.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bolikov.entity.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByTitleLike(String titlePattern);

    List<Product> findByTitleLikeAndCostGreaterThanEqual(String titlePattern, Integer minCost);

    List<Product> findByTitleLikeAndCostLessThanEqual(String titlePattern, Integer maxCost);

    List<Product> findByTitleLikeAndCostGreaterThanEqualAndCostLessThanEqual(String titlePattern, Integer minCost, Integer maxCost);

    List<Product> findByCostLessThanEqual(Integer maxCost);

    List<Product> findByCostGreaterThanEqual(Integer minCost);

    List<Product> findByCostGreaterThanEqualAndCostLessThanEqual(Integer minCost, Integer maxCost);

}
