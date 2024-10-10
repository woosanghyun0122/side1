package side.shopping.repository.users.dto.users;

import lombok.Getter;
import side.shopping.domain.users.Role;

@Getter
public class LoginResponseDto {

    private String userId;
    private String userName;
    private String nickName;
    private Role role;

    public LoginResponseDto() {
    }

    public LoginResponseDto(String userId, String userName, String nickName, Role role) {
        this.userId = userId;
        this.userName = userName;
        this.nickName = nickName;
        this.role = role;
    }
}
