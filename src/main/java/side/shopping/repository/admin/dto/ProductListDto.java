package side.shopping.repository.admin.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class ProductListDto {

    /**
     * 관리자 상품 조회용 dto
     * */

    private Long productId;
    private String name;
    private int price;
    private int quantity;
    private int saleCount;
    private String sellerId;
    private String sellerNickName;

    @QueryProjection
    public ProductListDto(Long productId, String name, int price, int quantity, int saleCount, String sellerId, String sellerNickName) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.saleCount = saleCount;
        this.sellerId = sellerId;
        this.sellerNickName = sellerNickName;
    }
}
