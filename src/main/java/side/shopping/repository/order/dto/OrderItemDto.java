package side.shopping.repository.order.dto;

import lombok.*;
import side.shopping.domain.order.Status;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {

    private long productId;
    private String productName;
    private int productPrice;
    private int amount;
    private int itemPrice;
    private String req;
    private Status status;
}
