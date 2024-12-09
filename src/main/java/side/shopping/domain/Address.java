package side.shopping.domain;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @NotBlank(message = "우편번호를 입력하세요")
    private String zipCode;

    @NotBlank(message = "주소를 입력하세요")
    private String address;
    private String addressDetail;

}
