package side.shopping.repository.order.dto;

import lombok.*;
import side.shopping.domain.order.Status;

import java.util.List;

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
    private String req;
    private Status status;



}


