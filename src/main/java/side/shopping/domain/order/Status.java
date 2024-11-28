package side.shopping.domain.order;

import lombok.Getter;

import java.util.HashMap;

@Getter
public enum Status {

    BEFORE_PAYMENT(1,"결제 대기"),
    PAYMENT_CONFIRMED(2,"결제 완료"),
    PACKING(3, "배송 준비 중"),
    DELIVERING (4, "배송 중"),
    ARRIVE(5,"배송 완료"),
    EXCHANGE(6, "교환 요청"),
    EXCHANGE_CONFIRM(7, "교환 승인"),
    REFUND(8, "환불 요청"),
    REFUND_CONFIRM(9, "환불 승인");
    ;

    private final int value;
    private final String description;

    private Status(int value,String description) {
        this.value = value;
        this.description = description;
    }


}
