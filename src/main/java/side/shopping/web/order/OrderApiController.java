package side.shopping.web.order;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import side.shopping.cache.CacheService;
import side.shopping.domain.cart.Cart;
import side.shopping.domain.order.Order;
import side.shopping.domain.order.OrderItem;
import side.shopping.exception.CustomException;
import side.shopping.repository.order.dto.FindOrderItemDto;
import side.shopping.repository.order.dto.OrderItemDto;
import side.shopping.repository.order.dto.UpdateOrderDto;
import side.shopping.repository.order.dto.UpdateOrderItemDto;
import side.shopping.repository.product.dto.FindProductDto;
import side.shopping.repository.users.dto.users.LoginResponseDto;
import side.shopping.web.cart.service.CartService;
import side.shopping.web.order.service.OrderItemService;
import side.shopping.web.order.service.OrderService;
import side.shopping.web.product.service.ProductService;

import java.util.ArrayList;
import java.util.List;

import static side.shopping.exception.ErrorCode.*;

@Slf4j
@RestController
@RequestMapping("/api/order")
public class OrderApiController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService itemService;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private CartService cartService;

    /**
     * 장바구니 -> 주문 하기
     */
    @PostMapping("/buyList")
    public ResponseEntity orderList(@RequestBody List<Long> list) {

        log.info("list={}", list);
        List<OrderItemDto> orderList = cartService.cartToOrderList(list);

        String key = cacheService.createKey();
        cacheService.setCacheValue(key,list);

        return ResponseEntity.status(HttpStatus.OK).body(key);
    }



    /**
     * 즉시 구매
     */
    @PostMapping("/buyInstant")
    public ResponseEntity<?> orderItem(@RequestBody @Validated OrderItemDto dto) {

        log.info("productName={}, productPrice={}", dto.getProductName(), dto.getProductPrice());

        List<OrderItemDto> list = new ArrayList<>();
        list.add(dto);

        String key = cacheService.createKey();
        cacheService.setCacheValue(key,list);

        return ResponseEntity.status(HttpStatus.OK).body(key);
    }


    /**
     * 주문 수정
     */
    @PutMapping("/modify-order")
    public ResponseEntity<?> modifyOrder(@RequestBody @Validated UpdateOrderDto dto) {

        Order modifyOrder = orderService.modifyOrder(dto);
        return ResponseEntity.status(HttpStatus.OK).body("주문 내역을 수정하였습니다.");
    }

    /**
     * 주문 개별 상품 수정
     */
    @PutMapping("/modify-orderItem")
    public ResponseEntity<?> modifyOrderItem(@RequestBody @Validated UpdateOrderItemDto dto) {

        OrderItem item = itemService.modifyOrderItem(dto);
        return ResponseEntity.status(HttpStatus.OK).body("요청에 성공하였습니다.");
    }




}
