package ru.bolikov.entity.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.bolikov.entity.users.User;

public final class UserSpecification {

    public static Specification<User> trueLiteral() {
        return (root, query, builder) -> builder.isTrue(builder.literal(true));
    }

    public static Specification<User> loginLike(String login) {
        return (root, query, builder) -> builder.like(root.get("login"), "%" + login + "%");
    }
}
