package side.shopping.repository.order.dto;

import lombok.*;
import side.shopping.domain.order.Status;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindOrderItemDto {

    private long productId;

    private String productName;

    private int productPrice;

    @Setter
    private int amount;

    private Status status;

    private String orderDate;



}
