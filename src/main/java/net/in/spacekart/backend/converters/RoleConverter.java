package net.in.spacekart.backend.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import net.in.spacekart.backend.database.enums.Role;

@Converter
public class RoleConverter implements AttributeConverter<Enum<Role>, String> {
    @Override
    public String convertToDatabaseColumn(Enum<Role> attribute) {
        return attribute.name();
    }

    @Override
    public Enum<Role> convertToEntityAttribute(String dbData) {
        return Role.valueOf(dbData);
    }
}
