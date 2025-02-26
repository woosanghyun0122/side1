package side.shopping.domain.payment;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPayment is a Querydsl query type for Payment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPayment extends EntityPathBase<Payment> {

    private static final long serialVersionUID = -1099821181L;

    public static final QPayment payment = new QPayment("payment");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final StringPath failReason = createString("failReason");

    public final EnumPath<side.shopping.domain.order.Method> method = createEnum("method", side.shopping.domain.order.Method.class);

    public final StringPath orderNum = createString("orderNum");

    public final NumberPath<Long> paymentId = createNumber("paymentId", Long.class);

    public final StringPath paymentKey = createString("paymentKey");

    public final BooleanPath paySuccessYN = createBoolean("paySuccessYN");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public QPayment(String variable) {
        super(Payment.class, forVariable(variable));
    }

    public QPayment(Path<? extends Payment> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPayment(PathMetadata metadata) {
        super(Payment.class, metadata);
    }

}

