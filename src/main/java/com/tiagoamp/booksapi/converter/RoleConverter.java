package com.tiagoamp.booksapi.converter;

import com.tiagoamp.booksapi.model.Role;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<Role, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Role role) {
        return role != null ? role.getId() : null;
    }

    @Override
    public Role convertToEntityAttribute(Integer id) {
        return id != null ? Role.of(id) : null;
    }
}
