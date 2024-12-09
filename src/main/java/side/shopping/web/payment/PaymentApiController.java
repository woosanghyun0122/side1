package side.shopping.web.payment;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import side.shopping.cache.CacheService;
import side.shopping.config.TossPaymentConfig;
import side.shopping.domain.order.Order;
import side.shopping.domain.users.Users;
import side.shopping.exception.CustomException;
import side.shopping.exception.ErrorCode;
import side.shopping.repository.payment.dto.PaymentDto;
import side.shopping.repository.payment.dto.PaymentResDto;
import side.shopping.repository.users.dto.users.LoginResponseDto;
import side.shopping.web.payment.service.PaymentService;

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


        Order myOrder = (Order) cacheService.getCacheValue(key);
        myOrder.setOrder(order);

        cacheService.removeCache(key);

        return ResponseEntity.ok().body(myOrder);

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
}
