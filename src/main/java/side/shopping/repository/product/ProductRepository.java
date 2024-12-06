package side.shopping.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
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

    //판매자 등록상품 조회
    List<Product> findByUser_Userid(String userid);

    @Query("select p from Product p where productId in :list")
    List<Product> findOrderList(@Param("list") Long[] list);

    @Modifying
    @Transactional
    @Query("update Product p set p.viewCount = p.viewCount +1 where p.productId = :productId")
    void updateViews(@Param("productId") long productId);

    @Modifying
    @Transactional
    @Query("update Product p set p.saleCount = p.saleCount +1 where p.productId = :productId")
    void updateSaleCount(@Param("productId") long productId);

    // 상품 등록
    Product save(Product product);

    // 삭제
    void deleteByProductId(long id);


}
