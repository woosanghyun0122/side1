package side.shopping.repository.admin.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaymentListDto {

    private String orderNum;

    private String paymentKey;

    private int price;

    private String paymentTime;

    @QueryProjection
    public PaymentListDto(String orderNum, String paymentKey, int price, String paymentTime) {
        this.orderNum = orderNum;
        this.paymentKey = paymentKey;
        this.price = price;
        this.paymentTime = paymentTime;
    }
}
