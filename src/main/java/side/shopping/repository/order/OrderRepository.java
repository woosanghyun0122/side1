package side.shopping.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import side.shopping.domain.order.Order;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

    // 회원별 주문 내역 조회
    List<Order> findByUser_UseridOrderByUpdatedAtDesc(String userid);
}
