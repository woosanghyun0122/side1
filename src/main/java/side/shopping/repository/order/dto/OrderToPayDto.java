package side.shopping.repository.order.dto;

import lombok.*;
import side.shopping.domain.Address;
import side.shopping.domain.order.Method;
import side.shopping.domain.order.Order;
import side.shopping.domain.order.OrderItem;
import side.shopping.domain.users.Users;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderToPayDto {

    private String orderNum;

    private String orderName;

    private String customerName;

    private String customerEmail;

    private String customerPhone;

    private String orderItemKey;

    private Users user;

    private int totalAmount;

    private String orderDate;

    private Address customerAddress;

    public Order toOrder() {

        return Order.builder()
                .orderNum(this.orderNum)
                .customerName(this.customerName)
                .address(this.customerAddress)
                .customerPhone(this.customerPhone)
                .user(this.user)
                .orderDate(this.orderDate)
                .build();
    }

    public String createOrderNum() {

        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("YYYYMMdd_HHmmss");
        String key = UUID.randomUUID()
                .toString().replace("-", "").substring(0, 8)
                + dateTimeFormatter.format(time);
        return key;
    }


}
