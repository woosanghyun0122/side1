package side.shopping.domain.users;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUsers is a Querydsl query type for Users
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUsers extends EntityPathBase<Users> {

    private static final long serialVersionUID = -659659129L;

    public static final QUsers users = new QUsers("users");

    public final StringPath address1 = createString("address1");

    public final StringPath address2 = createString("address2");

    public final DateTimePath<java.time.LocalDateTime> created_at = createDateTime("created_at", java.time.LocalDateTime.class);

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath nickName = createString("nickName");

    public final ListPath<side.shopping.domain.order.Order, side.shopping.domain.order.QOrder> orderList = this.<side.shopping.domain.order.Order, side.shopping.domain.order.QOrder>createList("orderList", side.shopping.domain.order.Order.class, side.shopping.domain.order.QOrder.class, PathInits.DIRECT2);

    public final StringPath password = createString("password");

    public final StringPath phone = createString("phone");

    public final EnumPath<Role> role = createEnum("role", Role.class);

    public final DateTimePath<java.time.LocalDateTime> updated_at = createDateTime("updated_at", java.time.LocalDateTime.class);

    public final StringPath userid = createString("userid");

    public final StringPath userName = createString("userName");

    public QUsers(String variable) {
        super(Users.class, forVariable(variable));
    }

    public QUsers(Path<? extends Users> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUsers(PathMetadata metadata) {
        super(Users.class, metadata);
    }

}

