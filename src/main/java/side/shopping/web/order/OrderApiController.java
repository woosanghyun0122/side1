package side.shopping.web.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import side.shopping.cache.CacheService;
import side.shopping.domain.order.Order;
import side.shopping.domain.order.OrderItem;
import side.shopping.repository.order.dto.UpdateOrderDto;
import side.shopping.repository.order.dto.UpdateOrderItemDto;
import side.shopping.repository.product.dto.FindProductDto;
import side.shopping.web.order.service.OrderItemService;
import side.shopping.web.order.service.OrderService;
import side.shopping.web.product.service.ProductService;

import java.math.BigInteger;
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

    /**
     * 주문 하기
     */

    @Cacheable(cacheNames = "orderList", key = "#uuid" )
    @PostMapping("/findOrderList")
    public ResponseEntity<?> orderList(@RequestBody Long[] list, String uuid) {

        List<FindProductDto> orderList = productService.findOrderList(list);
        uuid = createUuid();
        cacheService.setOrderList(uuid,orderList);

        return ResponseEntity.status(HttpStatus.OK).body(uuid);
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


    /**
     * 8자리 uuid 생성
     * */
    private static String createUuid() {

        String uuid = UUID.randomUUID().toString();
        BigInteger bigInt = new BigInteger(uuid, 16);

        String base62 = bigInt.toString(36);

        return base62.substring(0, 8);

    }

}
