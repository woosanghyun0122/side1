package side.shopping.web.payment.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import side.shopping.cache.CacheService;
import side.shopping.domain.order.Order;
import side.shopping.domain.payment.Payment;
import side.shopping.domain.users.Users;
import side.shopping.exception.CustomException;
import side.shopping.repository.payment.PaymentRepository;
import side.shopping.repository.users.UserRepository;
import side.shopping.web.category.CategoryService;
import side.shopping.web.product.service.ProductService;

import static side.shopping.exception.ErrorCode.SAVE_ERROR;
import static side.shopping.exception.ErrorCode.SELECT_ERROR;

@Slf4j
@Service
public class PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    CacheService cacheService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductService productService;



    /**
     *  결제하기 완
     * */
    @Transactional
    public Payment requestTossPayment(Payment payment, String userid, Order order) {

        // 회원정보 조회
        Users loginUser = userRepository.findByUserid(userid)
                .orElseThrow(() -> new CustomException(SELECT_ERROR.getCode(), SELECT_ERROR.getMessage()));

        // 주문에 회원 정보 세팅
        order.setUser(loginUser);
        // 결제에 주문 정보 세팅
        payment.setOrder(order);

        try {
            order.getOrderItemList().stream()
                    .forEach(item ->{
                        productService.saleCountUpdate(item.getId());
                    });
            return paymentRepository.save(payment);

        } catch (Exception e){
            throw new CustomException(SAVE_ERROR.getCode(), SAVE_ERROR.getMessage());
        }

    }


}
