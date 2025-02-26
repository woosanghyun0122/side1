package side.shopping.repository.admin.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * side.shopping.repository.admin.dto.QOrderListDto is a Querydsl Projection type for OrderListDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QOrderListDto extends ConstructorExpression<OrderListDto> {

    private static final long serialVersionUID = -763233578L;

    public QOrderListDto(com.querydsl.core.types.Expression<String> orderNum, com.querydsl.core.types.Expression<String> orderName, com.querydsl.core.types.Expression<String> userid, com.querydsl.core.types.Expression<String> userName, com.querydsl.core.types.Expression<String> customerName, com.querydsl.core.types.Expression<String> customerPhone, com.querydsl.core.types.Expression<String> orderDate, com.querydsl.core.types.Expression<String> orderModifyDate) {
        super(OrderListDto.class, new Class<?>[]{String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class}, orderNum, orderName, userid, userName, customerName, customerPhone, orderDate, orderModifyDate);
    }

}

