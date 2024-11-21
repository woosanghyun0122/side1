package side.shopping.repository.product.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SaveProductDto {

    @NotBlank(message = "상품명을 입력하세요")
    private String name;

    @NotNull(message = "금액을 입력해주세요")
    @Min(value = 100,message = "100원 이상 입력해주세요")
    @Max(value = 100000000,message = "100,000,000원 이상의 상품은 등록할 수 없습니다.")
    private int price;

    @NotBlank(message = "내용을 입력해주세요")
    private String content;

    @NotNull
    @Min(value = 10,message = "최소 등록 수량은 10개입니다.")
    @Max(value = 5000,message = "5000개 이상의 상품은 등록할 수 없습니다.")
    private int quantity;

    @NotNull(message = "카테고리 대분류를 선택해주세요")
    private String parentId;

    @NotNull(message = "카테고리 소분류를 선택해주세요")
    private String categoryId;

    public SaveProductDto() {
    }


    public SaveProductDto(String name, int price, String content, int quantity, String parentId, String categoryId) {
        this.name = name;
        this.price = price;
        this.content = content;
        this.quantity = quantity;
        this.parentId = parentId;
        this.categoryId = categoryId;
    }
}
