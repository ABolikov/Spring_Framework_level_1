package ru.bolikov.entity.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.bolikov.entity.users.Role;

public final class RoleSpecification {

    public static Specification<Role> trueLiteral() {
        return (root, query, builder) -> builder.isTrue(builder.literal(true));
    }

    public static Specification<Role> roleLike(String role) {
        return (root, query, builder) -> builder.like(root.get("roleName"), "%" + role + "%");
    }
}
