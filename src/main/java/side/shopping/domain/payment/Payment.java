package side.shopping.domain.payment;

import jakarta.persistence.*;
import lombok.*;
import side.shopping.domain.order.Method;

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
    private String orderNum;

    @Setter
    @Column(unique = true)
    private String paymentKey;

    @Setter
    @Column(name = "payment_method")
    @Enumerated(EnumType.STRING)
    private Method method;

    @Column
    private int price;

    @Setter
    @Column(name = "pay_success_yn")
    private boolean paySuccessYN;

/*    @Setter
    @Column(name = "cancel_reason")
    private String cancelReason;*/

    @Setter
    @Column
    private String failReason;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}
