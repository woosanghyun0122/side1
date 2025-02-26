package side.shopping.domain.order;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrder is a Querydsl query type for Order
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrder extends EntityPathBase<Order> {

    private static final long serialVersionUID = 1461308563L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrder order = new QOrder("order1");

    public final side.shopping.domain.QAddress address;

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final StringPath customerEmail = createString("customerEmail");

    public final StringPath customerName = createString("customerName");

    public final StringPath customerPhone = createString("customerPhone");

    public final ListPath<OrderItem, QOrderItem> orderItemList = this.<OrderItem, QOrderItem>createList("orderItemList", OrderItem.class, QOrderItem.class, PathInits.DIRECT2);

    public final StringPath orderName = createString("orderName");

    public final StringPath orderNum = createString("orderNum");

    public final side.shopping.domain.payment.QPayment payment;

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public final side.shopping.domain.users.QUsers user;

    public QOrder(String variable) {
        this(Order.class, forVariable(variable), INITS);
    }

    public QOrder(Path<? extends Order> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrder(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrder(PathMetadata metadata, PathInits inits) {
        this(Order.class, metadata, inits);
    }

    public QOrder(Class<? extends Order> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.address = inits.isInitialized("address") ? new side.shopping.domain.QAddress(forProperty("address")) : null;
        this.payment = inits.isInitialized("payment") ? new side.shopping.domain.payment.QPayment(forProperty("payment")) : null;
        this.user = inits.isInitialized("user") ? new side.shopping.domain.users.QUsers(forProperty("user")) : null;
    }

}

