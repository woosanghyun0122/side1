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
import side.shopping.repository.order.dto.OrderItemDto;
import side.shopping.repository.order.dto.OrderToPayDto;
import side.shopping.repository.order.dto.UpdateOrderDto;
import side.shopping.repository.order.dto.UserOrderListDto;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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

    @Setter
    @ManyToOne
    @JoinColumn(name = "userid", referencedColumnName = "userid", nullable = false)
    private Users user = new Users();

    @Column
    private String orderName;

    @OneToOne
    @JoinColumn(name = "paymentKey",referencedColumnName = "paymentKey")
    private Payment payment;

    @Column
    private String customerName;

    @Column
    private String customerEmail;

    @Column
    private String customerPhone;

    @Embedded
    private Address address;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<OrderItem> orderItemList;

    @Transient
    private String orderItemListKey;


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

    public void addOrderItem(OrderItem item) {
        this.orderItemList.add(item);
        item.setOrder(this);
    }


    public void updateToOrder(UpdateOrderDto dto) {

        Address modifyAddress = Address.builder()
                .zipCode(dto.getZipCode())
                .address(dto.getAddress())
                .addressDetail(dto.getAddressDetail())
                .build();

        this.orderName = dto.getName();
        this.address = modifyAddress;
    }

}
