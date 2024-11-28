package side.shopping.repository.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    private int amount;

    private Status status;

    private LocalDateTime orderDate;

}
