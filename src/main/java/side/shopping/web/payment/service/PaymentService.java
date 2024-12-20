package side.shopping.web.payment.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import side.shopping.cache.CacheService;
import side.shopping.config.TossPaymentConfig;
import side.shopping.domain.order.Method;
import side.shopping.domain.order.Order;
import side.shopping.domain.payment.Payment;
import side.shopping.domain.users.Users;
import side.shopping.exception.CustomException;
import side.shopping.repository.order.dto.OrderItemDto;
import side.shopping.repository.order.dto.OrderToPayDto;
import side.shopping.repository.payment.PaymentRepository;
import side.shopping.repository.payment.dto.PaymentResDto;
import side.shopping.repository.users.dto.users.LoginResponseDto;
import side.shopping.web.order.service.OrderService;
import side.shopping.web.product.service.ProductService;

import java.nio.charset.StandardCharsets;
import java.util.*;

import static side.shopping.exception.ErrorCode.*;

@Slf4j
@Service
public class PaymentService {

    @Autowired
    private TossPaymentConfig key;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    CacheService cacheService;

    @Autowired
    OrderService orderService;

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
     * 결제 성공 로직
     */
    @Transactional
    public PaymentResDto tossPaymentSuccess(String paymentKey, String orderId, int amount, LoginResponseDto loginUser) {

        PaymentResDto result = requestPaymentAccept(paymentKey, orderId, amount);

        log.info("orderId={}", orderId);

        String cacheKey = verifyPayment(orderId, amount);

        Payment payment = (Payment) cacheService.getCacheValue(cacheKey);
        payment.setPaymentKey(paymentKey);
        payment.setPaySuccessYN(true);
        payment.setMethod(getMethod(result.getMethod()));
        paymentRepository.save(payment);

        OrderToPayDto dto = (OrderToPayDto) cacheService.getCacheValue(orderId);
        dto.setUser(loginUser);


        log.info("success!! customerName={} customerPhone={} customerEmail={} zipCode={} address={}, totalPrice={}"
                , dto.getCustomerName(), dto.getCustomerPhone(), dto.getCustomerEmail(), dto.getZipcode(), dto.getAddress(),dto.getTotalAmount());


        List<OrderItemDto> dtoList = (List<OrderItemDto>) cacheService.getCacheValue(dto.getOrderItemKey());

        Order order = orderService.registerOrder(dto, dtoList);

        return result;

    }

    /**
     * 검증 및 주문 내역 저장
     */
    public String verifyPayment(String orderId, int amount) {

        log.info("verifyPayment");

        OrderToPayDto order = (OrderToPayDto) cacheService.getCacheValue(orderId);

            if (amount == order.getTotalAmount()) {

                log.info("savePayment={}", order.getOrderNum());

                Payment payment = Payment.builder()
                        .orderName(order.getOrderName())
                        .price(order.getTotalAmount())
                        .orderNum(order.getOrderNum())
                        .build();

                String cacheKey = cacheService.createKey();

                cacheService.setCacheValue(cacheKey, payment);
                return cacheKey;
            }
            else {
                throw new CustomException(PAYMENT_ERROR.getCode(), "결제 금액이 일치하지 않습니다.");
            }
    }


    /**
     * 최종 결제 응답 받아오기`
     * */
    @Transactional
    public PaymentResDto requestPaymentAccept(String paymentKey, String orderId, int amount) {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = getHeaders();
        JSONObject params = new JSONObject();
        params.put("orderId", orderId);
        params.put("amount", amount);

        PaymentResDto result = null;

        try {
            result = restTemplate.postForObject(key.URL + paymentKey,
                    new HttpEntity<>(params, headers),
                    PaymentResDto.class
            );

            log.info("result={}", result);
        } catch (Exception e) {
            log.error("Payment API Error: {}", e.getMessage(), e);
            throw new CustomException(PAYMENT_ERROR.getCode(), PAYMENT_ERROR.getMessage());
        }

        return result;
    }

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

    /**
     * 결제 실패 시 상태 수정
     * */
    @Transactional
    public Payment modifyPayment(Payment payment) {

        Payment modifyPayment = paymentRepository.findByPaymentKey(payment.getPaymentKey())
                .orElseThrow(()-> new CustomException(SELECT_ERROR.getCode(), SELECT_ERROR.getMessage()));

        modifyPayment.setPaySuccessYN(false);
        modifyPayment.setFailReason(payment.getFailReason());

        return modifyPayment;
    }


    private HttpHeaders getHeaders(){

        log.info("getHeaders");

        HttpHeaders headers = new HttpHeaders();
        String endcodedAuthKey = new String(
                Base64.getEncoder().encode(((key.getTestSecretKey()) + ":").getBytes(StandardCharsets.UTF_8))
        );

        log.info("endcodedAuthKey={}", endcodedAuthKey);

        headers.setBasicAuth(endcodedAuthKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        return headers;
    }

    private Method getMethod(String value) {

        Method[] methods = Method.values();
        int i=0;

        for (i=0; i< methods.length; i ++){

            if (methods[i].getValue().equals(value)) {
                return methods[i];
            }
        }

        return methods[i];
    }



}
