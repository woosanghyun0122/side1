package side.shopping.domain.order;

import lombok.Getter;

import java.util.HashMap;

@Getter
public enum Status {

    PAYMENT_CONFIRMED("PAYMENT_CONFIRMED","결제 완료"),
    PACKING("PACKING", "배송 준비 중"),
    DELIVERING ("DELIVERING", "배송 중"),
    ARRIVE("ARRIVE","배송 완료"),
    EXCHANGE("EXCHANGE", "교환 요청"),
    EXCHANGE_CONFIRM("EXCHANGE_CONFIRM", "교환 승인"),
    EXCHANGE_DENIED("EXCHANGE_DENIED","교환 거절"),
    REFUND("REFUND", "환불 요청"),
    REFUND_CONFIRM("REFUND_CONFIRM", "환불 승인"),
    REFUND_DENIED("REFUND_DENIED","환불 거절")
    ;


    private final String value;
    private final String description;

    private Status(String value,String description) {
        this.value = value;
        this.description = description;
    }


}
