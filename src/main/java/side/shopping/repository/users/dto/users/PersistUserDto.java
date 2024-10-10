package side.shopping.repository.users.dto.users;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import side.shopping.domain.users.Role;

@Getter
@Builder
@AllArgsConstructor
public class PersistUserDto {

    @NotBlank
    private String userid;

    @NotBlank
    private String password;

    @NotBlank
    private String nickName;

    @NotBlank
    private String userName;

    @NotBlank
    private String phone;

    private String email;

    @NotBlank
    private Role role;


}
