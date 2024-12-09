package side.shopping.repository.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import side.shopping.domain.payment.Payment;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {

    // 주문번호로 결제 조회
    Optional<Payment> findByOrder_OrderNum(String orderNum);
}
