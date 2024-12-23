package side.shopping.web.payment;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import side.shopping.cache.CacheService;
import side.shopping.config.TossPaymentConfig;
import side.shopping.domain.order.Method;
import side.shopping.domain.order.Order;
import side.shopping.domain.order.OrderItem;
import side.shopping.domain.payment.Payment;
import side.shopping.domain.product.Product;
import side.shopping.domain.users.Users;
import side.shopping.exception.CustomException;
import side.shopping.exception.ErrorCode;
import side.shopping.repository.order.dto.OrderItemDto;
import side.shopping.repository.order.dto.OrderToPayDto;
import side.shopping.repository.payment.dto.PaymentDto;
import side.shopping.repository.payment.dto.PaymentResDto;
import side.shopping.repository.users.dto.users.LoginResponseDto;
import side.shopping.web.payment.service.PaymentService;
import side.shopping.web.product.service.ProductService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static side.shopping.exception.ErrorCode.*;

@Slf4j
@RestController
@Validated
@RequestMapping("/api/v1/payments")
public class PaymentApiController {

    @Autowired
    CacheService cacheService;

    @Autowired
    private PaymentService paymentService;




    @PostMapping("/checkout")
    public ResponseEntity setOrder(@RequestBody @Validated OrderToPayDto order,HttpServletRequest request) {


        log.info("checkout!! customerName={} customerPhone={} customerEmail={} zipCode={} address={}"
                , order.getCustomerName(), order.getCustomerPhone(), order.getCustomerEmail(), order.getZipCode(), order.getAddress());

        List<OrderItemDto> list = (List<OrderItemDto>) cacheService.getCacheValue(order.getOrderItemKey());
        order.setOrderNum(order.createOrderNum());
        if (list.size() == 1) {
            order.setOrderName(list.get(0).getProductName());
        }else{
            order.setOrderName(list.get(0).getProductName() +" 외 "+(list.size() -1)+"건");
        }
        String key = order.getOrderNum();

        cacheService.setCacheValue(key, order);

        return ResponseEntity.status(HttpStatus.OK).body(key);

    }

}
