package side.shopping.repository.order.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderDto {

    private String orderNum;

    @NotBlank
    private String name;

    @NotBlank(message = "우편번호를 입력하세요")
    private String zipCode;

    @NotBlank(message = "주소를 입력하세요")
    private String address;

    private String addressDetail;



}
