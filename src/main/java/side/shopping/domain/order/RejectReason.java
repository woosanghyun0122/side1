package side.shopping.domain.order;

import lombok.Getter;

@Getter
public enum RejectReason {

    EXPIRED("EXPIRED","기한 만료"),
    NO_STOCK("NO_STOCK","사이즈 미스"),
    CUSTOMER_ERROR("CUSTOMER_ERROR","고객 과실")
    ;

    private final String value;
    private final String description;

    private RejectReason(String value, String description) {
        this.value = value;
        this.description = description;
    }
}
