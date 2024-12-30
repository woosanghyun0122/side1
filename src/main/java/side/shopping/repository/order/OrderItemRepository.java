package side.shopping.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import side.shopping.domain.order.OrderItem;
import side.shopping.domain.order.Status;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {

    // 주문 내역별 조회
    List<OrderItem> findByOrder_OrderNum(String orderNum);

    //교환 승인 대상 조회
    @Query("SELECT i FROM OrderItem i WHERE i.product.user.userid =:userid AND i.status =:status ORDER BY i.updatedAt DESC")
    List<OrderItem> findExchangeList(@Param("userid") String userid, @Param("status")Status status);

    //환불 승인 대상 조회
    @Query("SELECT i FROM OrderItem i WHERE i.product.user.userid =:userid AND i.status =:status ORDER BY i.updatedAt DESC")
    List<OrderItem> findRefundList(@Param("userid") String userid, @Param("status")Status status);


}
