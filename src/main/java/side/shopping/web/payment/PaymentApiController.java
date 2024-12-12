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
import side.shopping.domain.users.Users;
import side.shopping.exception.CustomException;
import side.shopping.exception.ErrorCode;
import side.shopping.repository.order.dto.OrderToPayDto;
import side.shopping.repository.payment.dto.PaymentDto;
import side.shopping.repository.payment.dto.PaymentResDto;
import side.shopping.repository.users.dto.users.LoginResponseDto;
import side.shopping.web.payment.service.PaymentService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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


    @PostMapping("/checkout/{key}")
    public ResponseEntity setOrder(@RequestBody @Validated Order order
            , @PathVariable(name = "key") String key
            ,HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        LoginResponseDto loginUser = (LoginResponseDto) session.getAttribute("loginUser");

        // redis에서 값가져오기
        Order myOrder = (Order) cacheService.getCacheValue(key);

        log.info("myorder={}", myOrder.getOrderItemList().get(0).getProduct().getName());

        settingOrder(myOrder, order, loginUser);

        OrderToPayDto dto = myOrder.toOrderToPayDto();
        cacheService.removeCache(key);

        String newKey = cacheService.createKey();
        cacheService.setOrderList(newKey, dto);

        return ResponseEntity.status(HttpStatus.OK).body(newKey);

    }

    @PostMapping("/toss")
    public ResponseEntity requestTossPayment(@RequestBody @Validated PaymentDto dto, HttpServletRequest request) {


        log.info("언제오나보자");
        HttpSession session = request.getSession(false);
        LoginResponseDto loginUser = (LoginResponseDto) session.getAttribute("loginUser");


        Order order = (Order) cacheService.getCacheValue(dto.getOrderKey());
        order.setAddress(dto.getAddress());

        PaymentResDto resDto = service.requestTossPayment(dto.toEntity(), loginUser.getUserId()
                , order).toPaymentResDto();
        resDto.setSuccessUrl(resDto.getSuccessUrl() == null ? config.getSuccessUrl() : resDto.getSuccessUrl());
        resDto.setFailUrl(resDto.getFailUrl() == null ? config.getFailUrl() : resDto.getFailUrl());

        return ResponseEntity.ok().body(resDto);

    }

    private void settingOrder(Order order1, Order order2, LoginResponseDto dto) {

        String orderName;

        if (!order1.getOrderItemList().isEmpty()) {
            orderName = order1.getOrderItemList().get(0).getProduct().getName() + "외 " + (order1.getOrderItemList().size() - 1) + "건";
        } else {
            orderName = "상품 없음";  // 리스트가 비어 있을 경우 처리
        }

        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("YYYY-MM-dd");

        LoginResponseDto loginUser = new LoginResponseDto();

        order1.setOrderNum(order2.createOrderNum());
        order1.setName(orderName);
        order1.setAddress(order2.getAddress());
        order1.setMethod(order2.getMethod());
        order1.setTotalCount(order1.registerTotalAmount(order1.getOrderItemList()));
        order1.setTotalPrice(order1.registerTotalPrice(order1.getOrderItemList()));
        order1.setOrderDate(dateTimeFormatter.format(time));
        order1.setUser(loginUser.toUsers(dto));

        log.info("setOrderNum={},setName={}, setAddress={},setMethod={},setTotalPrice={},setOrderDate={},setUserName={},setUserPhone={},setUserEmail={}",
                order1.getOrderNum(), order1.getName(), order1.getAddress(), order1.getMethod(), order1.getTotalPrice(), order1.getOrderDate(), order1.getUser().getUserName(), order1.getUser().getPhone(), order1.getUser().getEmail()
        );
    }
}
