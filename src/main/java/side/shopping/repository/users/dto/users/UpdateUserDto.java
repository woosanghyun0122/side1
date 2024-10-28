package side.shopping.repository.users.dto.users;

import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Getter
@Builder
public class UpdateUserDto {

    @NotNull
    private Long id;

    @NotBlank()
    private String nickName;

    @NotBlank(message = "-를 제외한 핸드폰 번호를 입력해주세요")
    @Size(min = 10, max=11,message = "핸드폰번호 11자리를 입력해주세요")
    private String phone;

    @NotNull
    private String email;

    @NotBlank(message = "주소를 입력하세요")
    private String address1;

    private String address2;

    public UpdateUserDto() {
    }

    public UpdateUserDto(Long id, String nickName, String phone, String email, String address1, String address2) {
        this.id = id;
        this.nickName = nickName;
        this.phone = phone;
        this.email = email;
        this.address1 = address1;
        this.address2 = address2;
    }
}
