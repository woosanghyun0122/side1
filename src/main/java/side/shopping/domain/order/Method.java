package side.shopping.domain.order;

import lombok.Getter;


@Getter
public enum Method {

    CARD("card", "신용카드"), ACCOUNT("account","무통장 입금"), PHONE("phone","휴대폰 결제");

    private final String value;
    private final String description;

    private Method(String value, String description) {

        this.value = value;
        this.description = description;
    }

}
