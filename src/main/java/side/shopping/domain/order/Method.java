package side.shopping.domain.order;

import lombok.Getter;


@Getter
public enum Method {

    CARD("CARD", "신용카드"), TRANSFER("TRANSFER","계좌이체"), MOBILE_PHONE("MOBILE_PHONE","휴대폰 결제");

    private final String value;
    private final String description;

    private Method(String value, String description) {

        this.value = value;
        this.description = description;
    }

}
