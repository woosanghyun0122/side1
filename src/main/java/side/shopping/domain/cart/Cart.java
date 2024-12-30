package side.shopping.domain.cart;

import jakarta.persistence.*;
import lombok.*;
import side.shopping.domain.product.Product;
import side.shopping.domain.users.Users;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    @Column
    private int amount;

    @Setter
    @OneToOne
    @JoinColumn(name = "userid", referencedColumnName = "userid")
    private Users user;

    @Column
    private LocalDateTime createdAt;

    @Column
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
