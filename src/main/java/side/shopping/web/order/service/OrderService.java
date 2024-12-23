package side.shopping.web.order.service;

import jakarta.persistence.EntityExistsException;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import side.shopping.domain.Address;
import side.shopping.domain.order.Order;
import side.shopping.domain.order.OrderItem;
import side.shopping.domain.payment.Payment;
import side.shopping.domain.product.Product;
import side.shopping.domain.users.Users;
import side.shopping.exception.CustomException;
import side.shopping.exception.ErrorCode;
import side.shopping.repository.order.OrderRepository;
import side.shopping.repository.order.dto.OrderItemDto;
import side.shopping.repository.order.dto.OrderToPayDto;
import side.shopping.repository.order.dto.UpdateOrderDto;
import side.shopping.repository.order.dto.UserOrderListDto;
import side.shopping.repository.payment.PaymentRepository;
import side.shopping.repository.product.ProductRepository;
import side.shopping.repository.users.UserRepository;
import side.shopping.repository.users.dto.users.LoginResponseDto;
import side.shopping.web.product.service.ProductService;
import side.shopping.web.users.service.UsersService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static side.shopping.exception.ErrorCode.*;

@Slf4j
@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PaymentRepository paymentRepository;


    /**
     * 회원별 주문 내역 조회하기
     */
    public List<Order> findOrderList(String userid) {

        if (!StringUtils.hasText(userid)) {
            throw new CustomException(VARIABLE_ERROR.getCode(), VARIABLE_ERROR.getMessage());
        }

        List<Order> userOrderList = repository.findByUser_UseridOrderByUpdatedAtDesc(userid);

        if (userOrderList.isEmpty()) {
            throw new CustomException(SELECT_ERROR.getCode(), SELECT_ERROR.getMessage());
        };

        return userOrderList;
    }

    /**
     * 주문 하기
     */
    @Transactional
    public Order registerOrder(OrderToPayDto dto, List<OrderItemDto> orderList) {

        // 주문 시 로그인 정보 저장
        Users customer = userRepository.findByUserid(dto.getUser().getUserId())
                .orElseThrow(() -> new CustomException(SELECT_ERROR.getCode(), SERVER_ERROR.getMessage()));

        // 결제 내역 조회
        Payment payment = paymentRepository.findByOrderNum(dto.getOrderNum())
                .orElseThrow(() -> new CustomException(SELECT_ERROR.getCode(), SELECT_ERROR.getMessage()));

        Order order = dto.toOrder();
        log.info("ordernum={}", order.getOrderNum());
        order.setUser(customer);
        order.setPayment(payment);

        List<OrderItem> itemList = orderList.stream()
                .map(item -> {
                    OrderItem orderItem = item.toOrderItem(item);
                    Product product = productRepository.findById(item.getProductId())
                            .orElseThrow(() -> new CustomException(SELECT_ERROR.getCode(), SELECT_ERROR.getMessage()));

                    log.info("product={}", product.getProductId());

                    orderItem.setProduct(product);
                    order.addOrderItem(orderItem);
                    return orderItem;
                }).collect(Collectors.toList());

        try {
            return repository.save(order);
        } catch (EntityExistsException | DataIntegrityViolationException e) {
            throw new CustomException(SAVE_ERROR.getCode(), SAVE_ERROR.getMessage());
        }
    }


    /**
     * 주문 내역 수정하기
     */
    @Transactional
    public Order modifyOrder(UpdateOrderDto dto) {

        try {
            Order order = repository.findById(dto.getOrderNum())
                    .orElseThrow(() -> new CustomException(SELECT_ERROR.getCode(),SELECT_ERROR.getMessage()));

            order.updateToOrder(dto);
            return order;
        } catch (Exception e) {
            throw new CustomException(UPDATE_ERROR.getCode(), UPDATE_ERROR.getMessage());
        }
    }


    /**
     * 저장 시 orderItem cascade를 위한 세팅
     * */
    private static Order toSaveOrder(Order order, List<OrderItem> orderList) {

        for (OrderItem orderItem : orderList) {
            order.getOrderItemList().add(orderItem);
        }
        return order;
    }



}
