package side.shopping.repository.admin;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import side.shopping.domain.order.Order;
import side.shopping.domain.order.QOrder;
import side.shopping.domain.payment.Payment;
import side.shopping.domain.payment.QPayment;
import side.shopping.domain.product.Product;
import side.shopping.domain.product.QProduct;
import side.shopping.domain.users.QUsers;
import side.shopping.domain.users.Users;
import side.shopping.repository.admin.dto.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.springframework.util.StringUtils.hasText;

@Slf4j
@Repository
@RequiredArgsConstructor
public class AdminRepositoryImpl implements AdminRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<UserListDto> findAllUser(Pageable pageable, AdminConditionDto dto) {

        String entity = "user";
        QUsers users = QUsers.users;

        List<UserListDto> usersList =  queryFactory
                                            .select
                                                    (Projections.fields(UserListDto.class, users.userid, users.userName
                                                            , users.nickName, users.phone, users.email, users.role))
                                            .from(users)
                                            .where(useridEq(dto.getUserid(), entity), betweenDate(parsingDate(dto.getStartDate()), parsingDate(dto.getEndDate()), entity))
                                            .offset(pageable.getOffset())
                                            .limit(pageable.getPageSize())
                                            .fetch();

        log.info("SQL 쿼리 결과: {}", usersList.get(0).getPhone());

        JPAQuery<Long> totalCount = queryFactory
                .select(users.count())
                .from(users)
                .where(useridEq(dto.getUserid(), entity), betweenDate(parsingDate(dto.getStartDate()), parsingDate(dto.getEndDate()), entity));


        return PageableExecutionUtils.getPage(usersList, pageable, totalCount::fetchOne);
    }

    @Override
    public Page<ProductListDto> findAllProduct(Pageable pageable, AdminConditionDto dto) {

        String entity = "product";
        QProduct product = QProduct.product;

        List<ProductListDto> productList = queryFactory
                .select(Projections.fields(ProductListDto.class, product.productId, product.name, product.price
                        , product.quantity, product.saleCount, product.user.userid.as("sellerId"), product.user.nickName.as("sellerNickName")))
                .from(product)
                .where(useridEq(dto.getUserid(), entity), betweenDate(parsingDate(dto.getStartDate()), parsingDate(dto.getEndDate()), entity))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> totalCount = queryFactory
                .select(product.count())
                .from(product)
                .where(useridEq(dto.getUserid(), entity), betweenDate(parsingDate(dto.getStartDate()), parsingDate(dto.getEndDate()), entity));

        return PageableExecutionUtils.getPage(productList, pageable, totalCount::fetchOne);
    }

    @Override
    public Page<OrderListDto> findAllOrder(Pageable pageable, AdminConditionDto dto) {

        String entity = "order";
        QOrder order = QOrder.order;

        List<OrderListDto> orderList = queryFactory
                .select(Projections.fields(OrderListDto.class,order.orderNum,order.orderName,order.user.userid,order.user.userName
                ,order.customerName,order.customerPhone,order.createdAt,order.updatedAt)
                )
                .from(order)
                .where(useridEq(dto.getUserid(), entity), betweenDate(parsingDate(dto.getStartDate()), parsingDate(dto.getEndDate()), entity),orderNumEq(dto.getOrderNum(),entity))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> totalCount = queryFactory
                .select(order.count())
                .from(order)
                .where(useridEq(dto.getUserid(), entity), betweenDate(parsingDate(dto.getStartDate()), parsingDate(dto.getEndDate()), entity));

        return PageableExecutionUtils.getPage(orderList, pageable, totalCount::fetchOne);
    }

    @Override
    public Page<PaymentListDto> findAllPayment(Pageable pageable, AdminConditionDto dto) {

        String entity = "payment";
        QPayment payment = QPayment.payment;

        List<PaymentListDto> paymentList = queryFactory
                .select(Projections.fields(PaymentListDto.class,payment.orderNum,payment.paymentKey,payment.price,payment.createdAt
                ))
                .from(payment)
                .where(useridEq(dto.getUserid(), entity), betweenDate(parsingDate(dto.getStartDate()), parsingDate(dto.getEndDate()), entity))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> totalCount = queryFactory
                .select(payment.count())
                .from(payment)
                .where(useridEq(dto.getUserid(), entity), betweenDate(parsingDate(dto.getStartDate()), parsingDate(dto.getEndDate()), entity));

        return PageableExecutionUtils.getPage(paymentList, pageable, totalCount::fetchOne);
    }

    private BooleanExpression useridEq(String useridCond, String entity) {

        QUsers users = QUsers.users;
        QProduct product = QProduct.product;
        QOrder order = QOrder.order;

        if (entity.equals("user")) {
            return hasText(useridCond) ? users.userid.eq(useridCond) : null;
        } else if (entity.equals("product")) {
            return hasText(useridCond) ? product.user.userid.eq(useridCond) : null;
        } else {
            return hasText(useridCond) ? order.user.userid.eq(useridCond) : null;
        }

    }


    private BooleanExpression betweenDate(LocalDateTime startDate, LocalDateTime endDate, String entity) {

        QUsers users = QUsers.users;
        QProduct product = QProduct.product;
        QOrder order = QOrder.order;
        QPayment payment = QPayment.payment;

        if (entity.equals("user")) {
            if (!startDate.toString().isEmpty() && !endDate.toString().isEmpty()) {
                return users.created_at.between(startDate, endDate);
            }
        } else if (entity.equals("product")) {
            if (!startDate.toString().isEmpty() && !endDate.toString().isEmpty()) {
                return product.createdAt.between(startDate, endDate);
            }
        } else if (entity.equals("order")) {
            if (!startDate.toString().isEmpty() && !endDate.toString().isEmpty()) {
                return order.createdAt.between(startDate, endDate);
            }
        } else {
            if (!startDate.toString().isEmpty() && !endDate.toString().isEmpty()) {
                return payment.createdAt.between(startDate, endDate);
            }
        }
        return null;
    }

    private BooleanExpression orderNumEq(String orderNumCond, String entity) {

        QOrder order = QOrder.order;
        QPayment payment = QPayment.payment;

        if (entity.equals("order")) {
            return hasText(orderNumCond) ? order.orderNum.eq(orderNumCond) : null;
        } else {
            return hasText(orderNumCond) ? payment.orderNum.eq(orderNumCond) : null;
        }
    }

    private LocalDateTime parsingDate(String date) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);
        LocalDateTime dateTime = localDate.atStartOfDay();
        log.info("dateTime={}", dateTime);
        return dateTime;
    }


}