package side.shopping.web.product;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import side.shopping.domain.product.Category;
import side.shopping.domain.product.Product;
import side.shopping.exception.CustomException;
import side.shopping.exception.ErrorCode;
import side.shopping.repository.product.dto.SaveProductDto;
import side.shopping.repository.product.dto.UpdateProductDto;
import side.shopping.repository.users.dto.users.LoginResponseDto;
import side.shopping.web.category.CategoryService;
import side.shopping.web.product.service.ProductService;

import java.util.List;

import static side.shopping.exception.ErrorCode.*;

@Slf4j
@RestController
@RequestMapping("/api/product")
public class ProductApiController {

    @Autowired
    private ProductService service;

    @Autowired
    private CategoryService categoryService;


    /**
     * 저장
     */
    @PostMapping("/add")
    public ResponseEntity<?> save(@RequestBody @Validated SaveProductDto product, HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        LoginResponseDto loginUser = (LoginResponseDto) session.getAttribute("loginUser");

        log.info("loginUSer={}", loginUser.getUserId());

        try {
            Product addProduct = service.save(product, loginUser);
            return ResponseEntity.status(HttpStatus.CREATED).body("정상적으로 등록되었습니다");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 상품입니다.");
        }
    }

    /**
     * 수정
     */
    @PutMapping("/modify")
    public ResponseEntity<?> modify(@RequestBody @Validated UpdateProductDto dto) {

        service.update(dto);
        return ResponseEntity.status(HttpStatus.OK).body("상품 정보를 수정하였습니다.");
    }

    /**
     * 삭제
     */
    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<?> delete(@PathVariable("productId") Long productId) {

        service.delete(productId);
        return ResponseEntity.status(HttpStatus.OK).body("상품을 삭제하였습니다.");
    }
}
