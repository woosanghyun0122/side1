package side.shopping.domain.order;

import lombok.Getter;

@Getter
public enum Reason {

    CHANGE_OF_MIND("CHANGE_OF_MIND","단순변심"),
    SIZE_MISS("SIZE_MISS","사이즈 미스"),
    DEFECTIVE_PRODUCT("DEFECTIVE_PRODUCT","상품하자"),
    DELIVERY_DELAY("DELIVERY_DELAY","배송지연"),
    ETC("ETC","기타")
    ;


    private final String value;
    private final String description;

    private Reason(String value, String description) {
        this.value = value;
        this.description = description;
    }
}
