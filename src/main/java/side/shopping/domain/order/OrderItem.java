package side.shopping.domain.order;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import side.shopping.domain.product.Product;
import side.shopping.domain.users.Users;
import side.shopping.repository.order.dto.UpdateOrderItemDto;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Table(name = "order_item")
@NoArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product = new Product();

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_num")
    private Order order;

    @Setter
    @Column
    private int amount;

    @Column
    private String req;

    @Column
    @ColumnDefault("1")
    @Enumerated(EnumType.ORDINAL)
    private Status status;

    @Setter
    @Column(name = "total_price")
    private int totalPrice;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;



    @Builder
    public OrderItem(Product product, Order order, int amount,Status status) {
        this.product = product;
        this.order = order;
        this.amount = amount;
        this.status = status;
    }

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void toOrderItem(UpdateOrderItemDto dto) {

        this.status = dto.getStatus();
        this.amount = dto.getAmount();
        this.req = dto.getReq();
    }


}
