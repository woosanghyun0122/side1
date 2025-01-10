package side.shopping.repository.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import side.shopping.domain.order.Order;
import side.shopping.domain.payment.Payment;
import side.shopping.domain.product.Product;
import side.shopping.domain.users.Users;
import side.shopping.repository.admin.dto.*;

public interface AdminRepository {

    // 관리자 회원 조회
    Page<UserListDto> findAllUser(Pageable pageable, AdminConditionDto dto);

    // 관리자 상품 조회
    Page<ProductListDto> findAllProduct(Pageable pageable, AdminConditionDto dto);

    // 관리자 주문 조회
    Page<OrderListDto> findAllOrder(Pageable pageable, AdminConditionDto dto);

    // 관리자 결제 조회
    Page<PaymentListDto> findAllPayment(Pageable pageable, AdminConditionDto dto);
}
