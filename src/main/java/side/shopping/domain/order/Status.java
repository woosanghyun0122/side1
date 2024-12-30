package side.shopping.domain.order;

import lombok.Getter;

import java.util.HashMap;

@Getter
public enum Status {

    PAYMENT_CONFIRMED(1,"결제 완료"),
    PACKING(2, "배송 준비 중"),
    DELIVERING (3, "배송 중"),
    ARRIVE(4,"배송 완료"),
    EXCHANGE(5, "교환 요청"),
    EXCHANGE_CONFIRM(6, "교환 승인"),
    EXCHANGE_DENIED(7,"교환 거절"),
    REFUND(8, "환불 요청"),
    REFUND_CONFIRM(9, "환불 승인"),
    REFUND_DENIED(10,"환불 거절")
    ;


    private final int value;
    private final String description;

    private Status(int value,String description) {
        this.value = value;
        this.description = description;
    }


}
