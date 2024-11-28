package side.shopping.repository.order.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserOrderListDto {

    private String orderNum;
    private String productName;
    private String name;
    private String phone;
    private String method;
    private int totalCount;
    private int totalPrice;
    private LocalDateTime orderDate;

}
