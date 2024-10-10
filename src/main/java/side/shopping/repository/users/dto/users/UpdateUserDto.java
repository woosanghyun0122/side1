package side.shopping.repository.users.dto.users;

import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class UpdateUserDto {

    @NotBlank
    private Long id;

    @NotBlank(message = "닉네임을 입력해주세요")
    private String nickname;

    @NotBlank
    @Length(min = 10, max=11)
    private String phone;

    @NotNull
    private String email;

    @NotBlank
    private String address1;

    private String address2;


}
