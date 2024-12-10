package side.shopping.repository.order.dto;

import lombok.*;
import side.shopping.domain.order.Method;
import side.shopping.domain.order.OrderItem;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderToPayDto {

    private String orderNum;

    private String orderName;

    private String customerName;

    private String customerEmail;

    private String userPhone;

    private Method method;

    private List<OrderItemDto> orderItemList;

    private int totalAmount;

    private int totalPrice;

    private String orderDate;


}
