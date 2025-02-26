package side.shopping.domain.zzim;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QZzim is a Querydsl query type for Zzim
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QZzim extends EntityPathBase<Zzim> {

    private static final long serialVersionUID = -1028981485L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QZzim zzim = new QZzim("zzim");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final side.shopping.domain.product.QProduct product;

    public final side.shopping.domain.users.QUsers user;

    public QZzim(String variable) {
        this(Zzim.class, forVariable(variable), INITS);
    }

    public QZzim(Path<? extends Zzim> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QZzim(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QZzim(PathMetadata metadata, PathInits inits) {
        this(Zzim.class, metadata, inits);
    }

    public QZzim(Class<? extends Zzim> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new side.shopping.domain.product.QProduct(forProperty("product"), inits.get("product")) : null;
        this.user = inits.isInitialized("user") ? new side.shopping.domain.users.QUsers(forProperty("user")) : null;
    }

}

