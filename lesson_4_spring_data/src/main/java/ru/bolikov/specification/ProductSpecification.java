package ru.bolikov.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.bolikov.entity.Product;

public final class ProductSpecification {

    public static Specification<Product> trueLiteral() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isTrue(criteriaBuilder.literal(true));
    }

    public static Specification<Product> productOrderBy(String sort, String parameter) {
        if (sort.equals("asc")) {
            return (root, query, criteriaBuilder) -> query.orderBy(criteriaBuilder.asc(root.get(parameter))).getRestriction();
        } else if (sort.equals("desc")) {
            return (root, query, criteriaBuilder) -> query.orderBy(criteriaBuilder.desc(root.get(parameter))).getRestriction();
        } else {
            return (root, query, criteriaBuilder) -> query.orderBy(criteriaBuilder.asc(root.get("id"))).getRestriction();
        }
    }

    public static Specification<Product> productTitleLike(String title) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), "%" + title + "%");
    }

    public static Specification<Product> productCostGreaterThanOrEqualTo(Integer cost_min) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("cost"), cost_min);
    }

    public static Specification<Product> productCostLessThanOrEqualTo(Integer cost_max) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("cost"), cost_max);
    }
}
