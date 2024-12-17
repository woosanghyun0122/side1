package side.shopping.repository.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import side.shopping.domain.payment.Payment;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {

    // 주문번호로 결제 조회
    @Query("SELECT p FROM Payment p WHERE p.orderNum = :orderNum and p.paySuccessYn = true")
    Optional<Payment> findByOrderNum(@Param("orderNum") String orderNum);

    // 결제 키로 결제 조회
    Optional<Payment> findByPaymentKey(String paymentKey);



}
