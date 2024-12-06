package side.shopping.web.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import side.shopping.cache.CacheKeyGenerator;
import side.shopping.cache.CacheService;
import side.shopping.domain.order.Order;
import side.shopping.domain.order.OrderItem;
import side.shopping.repository.order.dto.FindOrderItemDto;
import side.shopping.repository.order.dto.UpdateOrderDto;
import side.shopping.repository.order.dto.UpdateOrderItemDto;
import side.shopping.repository.product.dto.FindProductDto;
import side.shopping.web.order.service.OrderItemService;
import side.shopping.web.order.service.OrderService;
import side.shopping.web.product.service.ProductService;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/order")
public class OrderApiController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService itemService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CacheService cacheService;

//    /**
//     * 장바구니 -> 주문 하기
//     */
//    @PostMapping("/cartPay")
//    public ResponseEntity<?> orderList(@RequestBody Long[] list) {
//
//        return
//    }

    /**
     * 즉시 구매
     */
    @PostMapping("/buyInstant")
    public ResponseEntity<?> orderItem(@RequestBody FindOrderItemDto dto) {

        FindOrderItemDto item = productService.instantOrderItem(dto.getProductId());
        item.setAmount(dto.getAmount());

        List<FindOrderItemDto> list = new ArrayList<>();
        list.add(item);

        LocalDateTime time = LocalDateTime.now();
        String key = UUID.randomUUID()
                .toString().replace("-", "").substring(0, 8)
                + time;
        cacheService.setOrderList(key,list);

        return ResponseEntity.status(HttpStatus.OK).body(key);
    }


    /**
     * 주문 등록
     * */
    @PostMapping("/register")
    public ResponseEntity<?> save(@RequestBody @Validated Order order, List<OrderItem> orderList) {

        Order newOrder = orderService.registerOrder(order, orderList);
        return ResponseEntity.status(HttpStatus.CREATED).body("주문 되었습니다.");

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
