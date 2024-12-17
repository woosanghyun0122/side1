package side.shopping.repository.users.dto.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import side.shopping.domain.users.Role;
import side.shopping.domain.users.Users;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {

    private String userId;
    private String userName;
    private String nickName;
    private String phone;
    private String email;
    private Role role;

    public Users toUsers(){

        return Users.builder()
                .userid(this.userId)
                .userName(this.userName)
                .phone(this.phone)
                .email(this.email)
                .build();
    }

}

