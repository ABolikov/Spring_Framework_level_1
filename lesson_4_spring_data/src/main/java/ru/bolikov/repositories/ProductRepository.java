package ru.bolikov.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.bolikov.entity.Product;


@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {

    Page<Product> findByTitleLikeOrderByIdAsc(String titlePattern, Pageable pageable);

    Page<Product> findByTitleLikeAndCostGreaterThanEqualOrderByIdAsc(String titlePattern, Integer minCost, Pageable pageable);

    Page<Product> findByTitleLikeAndCostLessThanEqualOrderByIdAsc(String titlePattern, Integer maxCost, Pageable pageable);

    Page<Product> findByTitleLikeAndCostGreaterThanEqualAndCostLessThanEqualOrderByIdAsc(String titlePattern, Integer minCost, Integer maxCost, Pageable pageable);

    Page<Product> findByCostLessThanEqualOrderByIdAsc(Integer maxCost, Pageable pageable);

    Page<Product> findByCostGreaterThanEqualOrderByIdAsc(Integer minCost, Pageable pageable);

    Page<Product> findByCostGreaterThanEqualAndCostLessThanEqualOrderByIdAsc(Integer minCost, Integer maxCost, Pageable pageable);

}
