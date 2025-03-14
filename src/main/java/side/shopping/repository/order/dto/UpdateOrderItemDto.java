package side.shopping.repository.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import side.shopping.domain.order.Reason;
import side.shopping.domain.order.Status;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderItemDto {

    private long id;

    private int amount;

    private String cancelReason;

    private String rejectReason;

}
