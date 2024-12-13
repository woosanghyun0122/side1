package side.shopping.repository.payment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import side.shopping.domain.Address;
import side.shopping.domain.order.Method;
import side.shopping.domain.order.Order;
import side.shopping.domain.payment.Payment;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {

    @NotNull
    private Method method; // 결제 타입

    private int price;

    private Address address;

    @NotNull
    private String orderName;

    private String successfulUrl; // 성공 시 리다이렉트 url

    private String failUrl; // 실패 시 url

    private String orderNum;

    public Payment toEntity() {
        return Payment.builder()
                .method(method)
                .orderName(orderName)
                .paySuccessYN(false)
                .build();
    }

}
