package side.shopping.domain.users;

import lombok.Getter;

@Getter
public enum Role {

    ADMIN("ADMIN"), SELLER("SELLER"), NORMAL("NORMAL");

    private final String value;

    private Role(String value) {
        this.value = value;
    }

    public String getRole() {
        return this.value;
    }
}
