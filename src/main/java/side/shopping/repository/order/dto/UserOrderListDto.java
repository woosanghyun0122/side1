package side.shopping.repository.order.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import side.shopping.domain.order.OrderItem;

import java.time.LocalDateTime;
import java.util.List;


/**
 *
 * */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserOrderListDto {

    private LocalDateTime orderDate;

    private String orderNum;

    private int totalPrice;

    private List<OrderItem> orderItemList;

}
