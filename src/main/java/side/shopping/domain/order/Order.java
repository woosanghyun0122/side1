package side.shopping.domain.order;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import side.shopping.domain.Address;
import side.shopping.domain.users.Users;
import side.shopping.repository.order.dto.UpdateOrderDto;
import side.shopping.repository.order.dto.UserOrderListDto;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Builder
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_num")
    private String orderNum;

    @Column
    private String name;

    @Embedded
    private Address address;

    @Column
    private String phone;

    @Column(name = "payment_method")
    private String method;

    @Column
    private String req;

    @Setter
    private int totalCount;

    @Setter
    private int totalPrice;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "userid", referencedColumnName = "userid", nullable = false)
    private Users user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST)
    private List<OrderItem> orderItemList = new ArrayList<OrderItem>();

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Order() {
    }


    public int registerTotalPrice (List<OrderItem> orderItemList) {

        int totalPrice = 0;

        for (OrderItem orderItem : orderItemList) {
            totalPrice = totalPrice + orderItem.getTotalPrice();
        }

        return totalPrice;
    }

    public int registerTotalAmount(List<OrderItem> orderItemList) {
        return orderItemList.size();
    }


    public UserOrderListDto toUserOrderListDto(Order order){

        return UserOrderListDto.builder()
                .orderNum(order.getOrderNum())
                .productName(order.getOrderItemList().get(0).getProduct().getName() + " 외 " + order.orderItemList.size()+"건")
                .name(order.getName())
                .phone(order.getPhone())
                .method(order.getMethod())
                .totalCount(order.getTotalCount())
                .totalPrice(order.getTotalPrice())
                .orderDate(order.getOrderDate())
                .build();
    }

    public void updateToOrder(UpdateOrderDto dto) {

        Address address = Address.builder()
                .zipCode(dto.getZipCode())
                .address(dto.getAddress())
                .addressDetail(dto.getAddressDetail())
                .build();

        this.name = dto.getName();
        this.address = address;
    }






}
