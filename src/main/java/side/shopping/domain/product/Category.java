package side.shopping.domain.product;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Entity
@Builder
public class Category {

    @Id
    @Column(name="category_id")
    private String categoryId;

    @Column(name = "category_name")
    private String categoryName;

    @Column
    private int depth;

    @Column(name = "parent_id")
    private String parentId;

    @Column
    private LocalDateTime created_at;

    @Column
    private LocalDateTime updated_at;

    @PrePersist
    public void onCreate() {
        this.created_at = LocalDateTime.now();
        this.updated_at = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updated_at = LocalDateTime.now();
    }

    public Category() {
    }

    public Category(String categoryId, String categoryName, int depth, String parentId, LocalDateTime created_at, LocalDateTime updated_at) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.depth = depth;
        this.parentId = parentId;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public void update(Category category) {
        this.categoryName = category.getCategoryName();
    }
}
