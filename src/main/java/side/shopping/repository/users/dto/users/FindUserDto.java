package side.shopping.repository.users.dto.users;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FindUserDto {

    private String userid;

    @NotBlank(message = "-를 제외한 핸드폰 번호를 입력해주세요")
    @Size(min = 10, max = 11, message = "핸드폰번호 11자리를 입력해주세요")
    private String phone;

    @NotBlank(message = "이름을 입력해주세요")
    private String userName;

    private String email;

    public FindUserDto() {
    }

    public FindUserDto(String userid, String phone, String userName, String email) {
        this.userid = userid;
        this.phone = phone;
        this.userName = userName;
        this.email = email;
    }
}
