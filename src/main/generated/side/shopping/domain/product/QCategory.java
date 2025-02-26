package side.shopping.domain.product;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCategory is a Querydsl query type for Category
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCategory extends EntityPathBase<Category> {

    private static final long serialVersionUID = -1745081768L;

    public static final QCategory category = new QCategory("category");

    public final StringPath categoryId = createString("categoryId");

    public final StringPath categoryName = createString("categoryName");

    public final DateTimePath<java.time.LocalDateTime> created_at = createDateTime("created_at", java.time.LocalDateTime.class);

    public final NumberPath<Integer> depth = createNumber("depth", Integer.class);

    public final StringPath parentId = createString("parentId");

    public final DateTimePath<java.time.LocalDateTime> updated_at = createDateTime("updated_at", java.time.LocalDateTime.class);

    public QCategory(String variable) {
        super(Category.class, forVariable(variable));
    }

    public QCategory(Path<? extends Category> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCategory(PathMetadata metadata) {
        super(Category.class, metadata);
    }

}

