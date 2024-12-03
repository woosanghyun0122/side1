package side.shopping.web.order;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import side.shopping.domain.Address;
import side.shopping.domain.order.Order;
import side.shopping.domain.order.OrderItem;
import side.shopping.domain.order.Status;
import side.shopping.domain.product.Product;
import side.shopping.domain.users.Users;
import side.shopping.exception.CustomException;
import side.shopping.exception.ErrorCode;
import side.shopping.repository.order.OrderItemRepository;
import side.shopping.repository.order.OrderRepository;
import side.shopping.repository.order.dto.UpdateOrderDto;
import side.shopping.repository.order.dto.UpdateOrderItemDto;
import side.shopping.repository.order.dto.UserOrderListDto;
import side.shopping.repository.product.ProductRepository;
import side.shopping.repository.users.UserRepository;
import side.shopping.web.order.service.OrderItemService;
import side.shopping.web.order.service.OrderService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static side.shopping.exception.ErrorCode.*;

@SpringBootTest
@Slf4j
public class OrderTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PlatformTransactionManager transactionManager;
    TransactionStatus transactionStatus;




    @BeforeEach
    void beforeEach() {
        // 트랜잭션 시작
        transactionStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
    }

    @AfterEach
    void afterEach() {
        transactionManager.rollback(transactionStatus);
    }


    @Test
    @DisplayName("주문하기")
    void registerOrder() {

        List<OrderItem> itemList = settingItemList();
        Order saveOrder = orderService.registerOrder(setOrder(),itemList);

        itemList.stream()
                .forEach(item ->{
                    log.info("item.id={}", item.getId());
                    item.setTotalPrice(item);
                    item.setOrder(saveOrder);
                    saveOrder.getOrderItemList().add(item);
                });

        OrderItem item1 = orderItemRepository.findById(itemList.get(0).getId())
                .orElseThrow(() -> new CustomException(SELECT_ERROR.getCode(), SERVER_ERROR.getMessage()));

        OrderItem item2 = orderItemRepository.findById(itemList.get(1).getId())
                .orElseThrow(() -> new CustomException(SELECT_ERROR.getCode(), SERVER_ERROR.getMessage()));

        log.info("item1={}", item1.getOrder().getOrderNum());
        log.info("item2={}", item2.getOrder().getOrderNum());


        assertThat(saveOrder.getOrderNum()).isEqualTo(item1.getOrder().getOrderNum());

    }

    @Test
    @DisplayName("주문 내역 조회하기")
    void findOrder() {

        // 주문 내역 저장
        List<OrderItem> itemList = settingItemList();
        Order saveOrder = orderService.registerOrder(setOrder(),itemList);

        itemList.stream()
                .forEach(item ->{
                    log.info("item.id={}", item.getId());
                    item.setTotalPrice(item);
                    item.setOrder(saveOrder);
                    saveOrder.getOrderItemList().add(item);
                });

        // 주문 내역 조회
        List<UserOrderListDto> orderList = orderService.findOrderList(saveOrder.getUser().getUserid());

        // 테스트 코드
        assertThat(orderList).isNotNull();
    }

    @Test
    @DisplayName("주문 내역 수정")
    @Transactional
    void modifyOrder() {

        // 주문 내역 저장
        List<OrderItem> itemList = settingItemList();
        Order saveOrder = orderService.registerOrder(setOrder(),itemList);

        itemList.stream()
                .forEach(item ->{
                    log.info("item.id={}", item.getId());
                    item.setTotalPrice(item);
                    item.setOrder(saveOrder);
                    saveOrder.getOrderItemList().add(item);
                });

        // 수정 폼 등록
        UpdateOrderDto dto = UpdateOrderDto.builder()
                .orderNum(saveOrder.getOrderNum())
                .zipCode("999999")
                .address("인천 부평구")
                .addressDetail("열우물로 90")
                .build();

        log.info("saveOrder={}", saveOrder.getAddress().getZipCode());

        Order modifyOrder = new Order();
        modifyOrder = orderService.modifyOrder(dto);

        log.info("modifyOrder={}", modifyOrder.getAddress().getZipCode());

        assertThat(modifyOrder.getAddress().getZipCode()).isEqualTo("999999");
    }

    @Test
    @DisplayName("주문 상태 변경")
    void modifyStatus() {

        // 주문 내역 저장
        List<OrderItem> itemList = settingItemList();
        Order saveOrder = orderService.registerOrder(setOrder(),itemList);

        itemList.stream()
                .forEach(item ->{
                    log.info("item.id={}", item.getId());
                    item.setTotalPrice(item);
                    item.setOrder(saveOrder);
                    saveOrder.getOrderItemList().add(item);
                });

        // 주문 상태 변경
        UpdateOrderItemDto dto = UpdateOrderItemDto.builder()
                .id(itemList.get(0).getId())
                .status(Status.PACKING)
                .amount(10)
                .build();


        OrderItem modifyStatusItem = orderItemService.modifyOrderItem(dto);
        log.info("orderItem={}", modifyStatusItem.getOrder().getAddress().getZipCode());
        assertThat(modifyStatusItem.getStatus()).isEqualTo(Status.PACKING);
        assertThat(modifyStatusItem.getAmount()).isEqualTo(10);

    }


    /**
     * 주문 세팅
     */
    private Order setOrder() {

        Users cust1 = userRepository.findById(6L)
                .orElseThrow(() -> new CustomException(SELECT_ERROR.getCode(), SELECT_ERROR.getMessage()));

        Address address = Address.builder()
                .zipCode("123456")
                .address("서울시 영등포구")
                .addressDetail("123-1 번지")
                .build();


        return Order.builder()
                .user(cust1)
                .name(cust1.getUserName())
                .address(address)
                .phone(cust1.getPhone())
                .method("카드")
                .orderItemList(new ArrayList<>())
                .build();

    }



    /**
     *  주문 상품 세팅
     * */
    private List<OrderItem> settingItemList() {

        Product product1 = productRepository.findById(1L)
                .orElseThrow(() -> new CustomException(SELECT_ERROR.getCode(), SELECT_ERROR.getMessage()));
        Product product2 = productRepository.findById(2L)
                .orElseThrow(() -> new CustomException(SELECT_ERROR.getCode(), SELECT_ERROR.getMessage()));


        OrderItem item1 = OrderItem.builder()
                .product(product1)
                .amount(1)
                .status(Status.BEFORE_PAYMENT)
                .build();

        item1.setTotalPrice(item1);
        log.info("total price={}", item1.getTotalPrice());

        OrderItem item2 = OrderItem.builder()
                .product(product1)
                .amount(3)
                .status(Status.BEFORE_PAYMENT)
                .build();

        item1.setTotalPrice(item2);

        ArrayList<OrderItem> orderItemList = new ArrayList<>();
        orderItemList.add(item1);
        orderItemList.add(item2);

        return orderItemList;
    }
}
