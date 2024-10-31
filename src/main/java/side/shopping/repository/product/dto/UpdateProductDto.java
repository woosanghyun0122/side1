package side.shopping.repository.product.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpdateProductDto {

    @NotNull
    private Long productId;

    @NotBlank(message = "상품명을 입력하세요")
    private String name;

    @NotBlank(message = "금액을 입력하세요")
    @Size(min= 100, message = "100원 이상 입력해주세요")
    private int price;

    @NotBlank(message = "내용을 입력해주세요")
    private String content;

    @NotBlank(message = "수량을 입력하세요")
    @Size(min= 5, max = 1000, message = "수량은 5개 이상 1000이하 등록가능합니다.")
    private int quantity;

    public UpdateProductDto() {
    }

    public UpdateProductDto(Long productId, String name, int price, String content, int quantity) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.content = content;
        this.quantity = quantity;
    }
}
