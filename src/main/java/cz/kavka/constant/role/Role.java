package cz.kavka.constant.role;

import lombok.Getter;

@Getter
public enum Role {

    ADMIN("ADMIN"),
    USER("USER");

    private final String spec;

    Role(String spec){
        this.spec = spec;
    }
}
