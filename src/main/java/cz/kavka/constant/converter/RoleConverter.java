package cz.kavka.constant.converter;

import cz.kavka.constant.role.Role;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;

@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<Role, String> {


    @Override
    public String convertToDatabaseColumn(Role role) {
        if (role == null) {
            return null;
        }
        return role.getSpec();
    }

    @Override
    public Role convertToEntityAttribute(String spec) {
        return Arrays.stream(Role.values())
                .filter(role -> role.getSpec().equals(spec))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
