package side.shopping.domain.payment;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import side.shopping.domain.order.Method;
import side.shopping.domain.order.Order;
import side.shopping.domain.users.Users;
import side.shopping.repository.payment.dto.PaymentResDto;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @Column
    private String orderName;

    @Column
    private int price;

    @Column(name = "payment_method")
    @Enumerated(EnumType.STRING)
    private Method method;

    @Column(unique = true)
    private String paymentKey;

    @Column(name = "pay_success_yn")
    private boolean paySuccessYN;

    @Column(name = "cancel_reason")
    private String cancelReason;

    @Column
    private String failReason;

    @Setter
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "orderNum")
    private Order order;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;



    public PaymentResDto toPaymentResDto() {

        return PaymentResDto.builder()
                .method(method.getDescription())
                .price(order.getTotalPrice())
                .orderName(orderName)
                .orderNum(order.getOrderNum())
                .customerEmail(order.getUser().getEmail())
                .customerName(order.getCustomerName())
                .createdAt(String.valueOf(order.getCreatedAt()))
                .cancelYN(paySuccessYN)
                .failReason(failReason)
                .build();
    }



}
