package side.shopping.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import side.shopping.domain.product.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


    // 판매량 순 동일 시 조회수 순 5개
    List<Product> findTop5ByOrderBySaleCountDescViewCountDesc();

    // 최신 등록 상품
    List<Product> findTop5ByOrderByCreatedAtDesc();

    // 카테고리별 조회
    List<Product> findByCategory_CategoryId(String categoryId);

    // 상위 카테고리별 조회
    List<Product> findByCategory_ParentId(String parentId);

    // 상품 등록
    Product save(Product product);

    // 삭제
    void deleteByProductId(long id);


}
