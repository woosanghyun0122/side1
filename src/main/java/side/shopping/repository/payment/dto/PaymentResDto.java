package side.shopping.repository.payment.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResDto {


    private String method;
    private String orderName; // 주문 명
    private int price; // 가격
    private String customerEmail;
    private String customerName;
    private String successUrl;
    private String failUrl;
    private String failReason;
    private boolean cancelYN;
    private String cancelReason;
    private String createdAt;
    private String orderId;
    private String type;

}
