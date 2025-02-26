package side.shopping.repository.admin.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * side.shopping.repository.admin.dto.QProductListDto is a Querydsl Projection type for ProductListDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QProductListDto extends ConstructorExpression<ProductListDto> {

    private static final long serialVersionUID = 1643540821L;

    public QProductListDto(com.querydsl.core.types.Expression<Long> productId, com.querydsl.core.types.Expression<String> name, com.querydsl.core.types.Expression<Integer> price, com.querydsl.core.types.Expression<Integer> quantity, com.querydsl.core.types.Expression<Integer> saleCount, com.querydsl.core.types.Expression<String> sellerId, com.querydsl.core.types.Expression<String> sellerNickName) {
        super(ProductListDto.class, new Class<?>[]{long.class, String.class, int.class, int.class, int.class, String.class, String.class}, productId, name, price, quantity, saleCount, sellerId, sellerNickName);
    }

}

