package side.shopping.domain.order;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;


@Getter
public enum Method {

    CARD("카드")
    , VIRTUAL_ACCOUNT("가상계좌")
    , SIMPLE_PAYMENT("간편결제")
    , MOBILE_PHONE("휴대폰")
    , ACCOUNT_TRANSFER("계좌이체");


    private final String value;

    private Method(String value) {

        this.value = value;
    }

}
