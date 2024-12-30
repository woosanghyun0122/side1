package side.shopping.repository.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import side.shopping.domain.cart.Cart;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("SELECT c FROM Cart c WHERE c.id IN :carts")
    List<Cart> findByCartList(@Param("carts") List<Long> list);

    // 장바구니 조회
    List<Cart> findByUser_Userid(String userid);

    @Modifying
    @Transactional
    @Query("DELETE FROM Cart c WHERE c.id IN :carts")
    void deleteProduct(@Param("carts") List<Long> list);

}
