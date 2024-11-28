package side.shopping.domain.product;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import side.shopping.domain.order.OrderItem;
import side.shopping.domain.users.Users;
import side.shopping.repository.product.dto.SaveProductDto;
import side.shopping.repository.product.dto.UpdateProductDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private int price;

    @Column(name = "content")
    private String content;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "sale_count")
    @ColumnDefault("0")
    private int saleCount;

    @Column(name = "view_count")
    @ColumnDefault("0")
    private int viewCount;

    @ManyToOne
    @JoinColumn(name = "userid", referencedColumnName = "userid", nullable = false)
    // referencedColumnName : 참조하는 컬럼이 기본 키 컬럼이 아니라면 명시적으로 해줘야한다.
    private Users user;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;


    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
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

    public Product() {
    }

    @Builder
    public Product(Long productId, String name, int price, String content, int quantity, int saleCount, int viewCount, Users user, Category category) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.content = content;
        this.quantity = quantity;
        this.saleCount = saleCount;
        this.viewCount = viewCount;
        this.user = user;
        this.category = category;
    }


    public void modify(UpdateProductDto dto) {

        this.name = dto.getName();
        this.content = dto.getContent();
        this.price = dto.getPrice();
        this.quantity = dto.getQuantity();
    }

    public Product saveDto(SaveProductDto dto,Category category,Users user) {

        return  Product.builder()
                .name(dto.getName())
                .price(dto.getPrice())
                .content(dto.getContent())
                .quantity(dto.getQuantity())
                .category(category)
                .user(user)
                .build();

    }
}
