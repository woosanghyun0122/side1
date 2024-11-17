package side.shopping.domain.product;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.boot.autoconfigure.web.WebProperties;
import side.shopping.domain.users.Users;
import side.shopping.repository.product.dto.UpdateProductDto;

import java.time.LocalDateTime;

@Getter
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "name")
    @NotBlank(message = "상품명을 입력하세요")
    private String name;

    @Column(name = "price")
    @NotNull(message = "금액을 입력해주세요")
    @Min(value = 100,message = "100원 이상 입력해주세요")
    @Max(value = 100000000,message = "100,000,000원 이상의 상품은 등록할 수 없습니다.")
    private int price;

    @Column(name = "content")
    @NotBlank(message = "내용을 입력해주세요")
    private String content;

    @Column(name = "quantity")
    @NotNull
    @Min(value = 10,message = "최소 등록 수량은 10개입니다.")
    @Max(value = 5000,message = "5000개 이상의 상품은 등록할 수 없습니다.")
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
    public Product(String name, int price, String content, int quantity, int saleCount, int viewCount, Users user, Category category, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.name = name;
        this.price = price;
        this.content = content;
        this.quantity = quantity;
        this.saleCount = saleCount;
        this.viewCount = viewCount;
        this.user = user;
        this.category = category;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void modify(UpdateProductDto dto) {

        this.name = dto.getName();
        this.content = dto.getContent();
        this.price = dto.getPrice();
        this.quantity = dto.getQuantity();
    }
}
