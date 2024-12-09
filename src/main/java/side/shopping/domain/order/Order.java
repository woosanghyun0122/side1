package side.shopping.domain.order;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import side.shopping.domain.Address;
import side.shopping.domain.payment.Payment;
import side.shopping.domain.users.Users;
import side.shopping.repository.order.dto.UpdateOrderDto;
import side.shopping.repository.order.dto.UserOrderListDto;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Order {

    @Id
    @Column(name = "order_num")
    private String orderNum;

    @Column
    private String name;

    @Embedded
    private Address address;

    @Column
    private String phone;

    @Column(name = "payment_method")
    @Enumerated(EnumType.STRING)
    private Method method;

    private String req;

    private int totalCount;

    private int totalPrice;

    @Column(name = "order_date")
    private String orderDate;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Setter
    @ManyToOne
    @JoinColumn(name = "userid", referencedColumnName = "userid", nullable = false)
    private Users user = new Users();

    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST)
    private List<OrderItem> orderItemList = new ArrayList<OrderItem>();

    @OneToOne(mappedBy = "order")
    private Payment payment;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.orderDate = LocalDateTime.now().toString().substring(0,8);
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
                .method(String.valueOf(order.getMethod()))
                .totalCount(order.getTotalCount())
                .totalPrice(order.getTotalPrice())
                .build();
    }

    public void updateToOrder(UpdateOrderDto dto) {

        Address modifyAddress = Address.builder()
                .zipCode(dto.getZipCode())
                .address(dto.getAddress())
                .addressDetail(dto.getAddressDetail())
                .build();

        this.name = dto.getName();
        this.address = modifyAddress;
    }

    public void setOrder(Order order) {

        this.orderNum = createOrderNum();
        this.name = order.getName();
        this.address = order.getAddress();
        this.method = order.getMethod();
        this.totalCount = order.registerTotalAmount(order.getOrderItemList());
        this.totalPrice = order.registerTotalPrice(order.getOrderItemList());
        this.orderDate = LocalDateTime.now().toString().substring(0,8);

    }

    public String createOrderNum() {

        LocalDateTime time = LocalDateTime.now();
        String key = UUID.randomUUID()
                .toString().replace("-", "").substring(0, 8)
                + time;
        return key;
    }






}
