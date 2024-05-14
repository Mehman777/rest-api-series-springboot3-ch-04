package com.tiagoamp.booksapi.model;

import com.tiagoamp.booksapi.exception.BusinessException;
import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;
import java.util.stream.Stream;

@Getter
public enum Role implements Serializable {

    ROLE_ADMIN(1, "ROLE_ADMIN"),

    ROLE_USER(2, "ROLE_USER");


    private final Integer id;

    private final String name;

    private Role(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Role lookUp(Integer id) throws BusinessException {
        for (Role role : values()) {
            if (role.id.equals(id)) {
                return role;
            }
        }
        throw new BusinessException(BusinessException.BusinessError.UNKNOWN_ROLE);
    }

    public static Role of(Integer id) {
        return Stream.of(Role.values())
                .filter(p -> Objects.equals(p.getId(), id))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
