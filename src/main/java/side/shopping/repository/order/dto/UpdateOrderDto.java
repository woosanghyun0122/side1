package side.shopping.repository.order.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import side.shopping.domain.Address;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderDto {

    private String orderNum;

    private String customerName;

    private String customerPhone;

    private String customerEmail;

    private Address address;
}
