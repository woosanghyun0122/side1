package side.shopping.repository.admin.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * side.shopping.repository.admin.dto.QPaymentListDto is a Querydsl Projection type for PaymentListDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QPaymentListDto extends ConstructorExpression<PaymentListDto> {

    private static final long serialVersionUID = 781677662L;

    public QPaymentListDto(com.querydsl.core.types.Expression<String> orderNum, com.querydsl.core.types.Expression<String> paymentKey, com.querydsl.core.types.Expression<Integer> price, com.querydsl.core.types.Expression<String> paymentTime) {
        super(PaymentListDto.class, new Class<?>[]{String.class, String.class, int.class, String.class}, orderNum, paymentKey, price, paymentTime);
    }

}

