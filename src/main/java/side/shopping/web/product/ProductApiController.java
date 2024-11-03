package side.shopping.web.product;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import side.shopping.domain.product.Product;
import side.shopping.repository.product.dto.UpdateProductDto;
import side.shopping.web.product.service.ProductService;

@Slf4j
@RestController
@RequestMapping("/api/product")
public class ProductApiController {

    @Autowired
    private ProductService service;

    @PostMapping("/add")
    public ResponseEntity<?> save(@RequestBody @Validated Product product){

        try {
            Product addProduct = service.save(product);
            return ResponseEntity.status(HttpStatus.CREATED).body("정상적으로 등록되었습니다");
        }catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 상품입니다.");
        }
    }

    @PutMapping("/modify")
    public ResponseEntity<?> modify(@RequestBody @Validated UpdateProductDto dto) {

        service.update(dto);
        return ResponseEntity.status(HttpStatus.OK).body("상품 정볼를 수정하였습니다.");
    }
}
