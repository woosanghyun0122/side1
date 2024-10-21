package side.shopping.repository.users.dto.users;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import side.shopping.domain.users.Role;

@Getter
@Builder
@AllArgsConstructor
public class PersistUserDto {

    @NotBlank(message = "아이디를 입력하세요")
    private String userid;

    @NotBlank(message = "비밀번호를 입력하세요")
    private String password;

    @NotBlank(message = "닉네임을 입력하세요")
    private String nickName;

    @NotBlank(message = "이름을 입력하세요")
    private String userName;

    @NotBlank(message = "-를 제외한 핸드폰 번호를 입력하세요")
    private String phone;

    @NotBlank(message = "주소를 입력하세요")
    private String address1;

    private String address2;

    private String email;

    @NotNull
    private Role role;


}
