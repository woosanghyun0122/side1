package side.shopping.web.payment.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import side.shopping.cache.CacheService;
import side.shopping.domain.order.Order;
import side.shopping.domain.payment.Payment;
import side.shopping.domain.users.Users;
import side.shopping.exception.CustomException;
import side.shopping.repository.payment.PaymentRepository;
import side.shopping.repository.users.UserRepository;
import side.shopping.web.category.CategoryService;
import side.shopping.web.product.service.ProductService;

import static side.shopping.exception.ErrorCode.*;

@Slf4j
@Service
public class PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    CacheService cacheService;

    @Autowired
    ProductService productService;

    /**
     * 결제 내역 조회
     */
    public Payment findByPaymentKey(String key) {

        if (!StringUtils.hasText(key)) {
            throw new CustomException(VARIABLE_ERROR.getCode(), VARIABLE_ERROR.getMessage());
        }

        return paymentRepository.findByPaymentKey(key)
                .orElseThrow(() -> new CustomException(SELECT_ERROR.getCode(), SERVER_ERROR.getMessage()));
    }


    /**
     * 유저별 결제 내역 조회
     */


    /**
     *  결제하기 완
     * */
    @Transactional
    public Payment savePayment(Payment payment) {

        try{
            return paymentRepository.save(payment);
        } catch (Exception e){
            throw new CustomException(SAVE_ERROR.getCode(), SAVE_ERROR.getMessage());
        }

    }


}
