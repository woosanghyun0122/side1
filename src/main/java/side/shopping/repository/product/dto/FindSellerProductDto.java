package side.shopping.repository.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class FindSellerProductDto {

    private Long productId;

    private String name;

    private int price;

    private int quantity;

    private int saleCount;

    public FindSellerProductDto() {
    }
}


