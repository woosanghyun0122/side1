package side.shopping.repository.order.dto;

import lombok.*;
import side.shopping.domain.Address;
import side.shopping.domain.order.Method;
import side.shopping.domain.order.Order;
import side.shopping.domain.order.OrderItem;
import side.shopping.domain.users.Users;
import side.shopping.repository.users.dto.users.LoginResponseDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

    private LoginResponseDto user;

    private int totalAmount;

    private String orderDate;

    private String zipcode;

    private String address;

    private String addressDetail;



    public Order toOrder() {

        Address customerAddress = Address.builder()
                .zipCode(this.zipcode)
                .address(this.address)
                .addressDetail(this.addressDetail)
                .build();

        return Order.builder()
                .orderNum(this.orderNum)
                .orderName(this.orderName)
                .customerName(this.customerName)
                .address(customerAddress)
                .customerPhone(this.customerPhone)
                .orderDate(this.orderDate)
                .orderItemList(new ArrayList<>())
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
