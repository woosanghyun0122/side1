package side.shopping.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import side.shopping.domain.order.OrderItem;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {

    // 주문 내역별 조회
    List<OrderItem> findByOrder_OrderNum(String orderNum);

    //판매자 판매 내역 조회



}
