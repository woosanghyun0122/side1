package side.shopping.web.payment;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import side.shopping.cache.CacheService;
import side.shopping.config.TossPaymentConfig;
import side.shopping.domain.order.Order;
import side.shopping.domain.order.OrderItem;
import side.shopping.domain.payment.Payment;
import side.shopping.domain.users.Users;
import side.shopping.exception.CustomException;
import side.shopping.exception.ErrorCode;
import side.shopping.repository.order.dto.OrderItemDto;
import side.shopping.repository.order.dto.OrderToPayDto;
import side.shopping.repository.payment.dto.PaymentDto;
import side.shopping.repository.payment.dto.PaymentResDto;
import side.shopping.repository.users.dto.users.LoginResponseDto;
import side.shopping.web.payment.service.PaymentService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import static side.shopping.exception.ErrorCode.*;

@Slf4j
@RestController
@Validated
@RequestMapping("/api/v1/payments")
public class PaymentApiController {

    private final TossPaymentConfig config;
    private final PaymentService service;

    @Autowired
    CacheService cacheService;

    public PaymentApiController(TossPaymentConfig config, PaymentService service) {
        this.config = config;
        this.service = service;
    }


    @PostMapping("/checkout")
    public ResponseEntity setOrder(@RequestBody @Validated OrderToPayDto order) {

        List<OrderItemDto> list = (List<OrderItemDto>) cacheService.getCacheValue(order.getOrderItemKey());
        order.setOrderNum(order.createOrderNum());
        if (list.size() == 1) {
            order.setOrderName(list.get(0).getProductName());
        }else{
            order.setOrderName(list.get(0).getProductName() +" 외 "+(list.size() -1)+"건");
        }
        String key = cacheService.createKey();
        cacheService.setCacheValue(key, order);

        return ResponseEntity.status(HttpStatus.OK).body(key);

    }
    @PostMapping("/toss/{key}")
    public ResponseEntity requestTossPayment(@RequestBody @Validated PaymentDto dto, @PathVariable("key") String key, HttpServletRequest request) {

        OrderToPayDto order = (OrderToPayDto) cacheService.getCacheValue(key);

        if (dto.getPrice() == order.getTotalAmount() && dto.getOrderNum().equals(order.getOrderNum())) {
            String paymentKey = cacheService.createKey();
            return ResponseEntity.status(HttpStatus.OK).body(paymentKey);
        } else {
            throw new CustomException(PAYMENT_ERROR.getCode(), PAYMENT_ERROR.getMessage());
        }
    }

    private void settingOrder(Order order1, Order order2, LoginResponseDto dto) {

/*        String orderName;

        if (!order1.getOrderItemList().isEmpty()) {
            orderName = order1.getOrderItemList().get(0).getProduct().getName() + "외 " + (order1.getOrderItemList().size() - 1) + "건";
        } else {
            orderName = "상품 없음";  // 리스트가 비어 있을 경우 처리
        }

        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("YYYY-MM-dd");

        LoginResponseDto loginUser = new LoginResponseDto();

        order1.setOrderNum(order2.createOrderNum());
        order1.setOrderName(orderName);
        order1.setAddress(order2.getAddress());
        order1.setMethod(order2.getMethod());
        order1.setTotalCount(order1.registerTotalAmount(order1.getOrderItemList()));
        order1.setTotalPrice(order1.registerTotalPrice(order1.getOrderItemList()));
        order1.setOrderDate(dateTimeFormatter.format(time));
        order1.setUser(loginUser.toUsers(dto));

        log.info("setOrderNum={},setName={}, setAddress={},setMethod={},setTotalPrice={},setOrderDate={},setUserName={},setUserPhone={},setUserEmail={}",
                order1.getOrderNum(), order1.getOrderName(), order1.getAddress(), order1.getMethod(), order1.getTotalPrice(), order1.getOrderDate(), order1.getUser().getUserName(), order1.getUser().getPhone(), order1.getUser().getEmail()
        );*/
    }
}
