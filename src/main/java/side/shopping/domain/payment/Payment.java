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

    @Setter
    @Column(name = "pay_success_yn")
    private boolean paySuccessYN;

    @Setter
    @Column(name = "cancel_reason")
    private String cancelReason;

    @Column
    private String failReason;

    @Column
    private String orderNum;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

}
