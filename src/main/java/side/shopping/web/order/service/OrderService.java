package side.shopping.web.order.service;

import jakarta.persistence.EntityExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import side.shopping.domain.Address;
import side.shopping.domain.order.Order;
import side.shopping.domain.order.OrderItem;
import side.shopping.domain.product.Product;
import side.shopping.exception.CustomException;
import side.shopping.exception.ErrorCode;
import side.shopping.repository.order.OrderRepository;
import side.shopping.repository.order.dto.UpdateOrderDto;
import side.shopping.repository.order.dto.UserOrderListDto;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static side.shopping.exception.ErrorCode.*;

@Slf4j
@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    /**
     * 회원별 주문 내역 조회하기
     */
    public List<UserOrderListDto> findOrderList(String userid) {

        if (!StringUtils.hasText(userid)) {
            throw new CustomException(VARIABLE_ERROR.getCode(), VARIABLE_ERROR.getMessage());
        }

        List<Order> userOrderList = repository.findByUser_UseridOrderByOrderDateDesc(userid);

        if (userOrderList.isEmpty()) {
            throw new CustomException(SELECT_ERROR.getCode(), SELECT_ERROR.getMessage());
        };

        return userOrderList.stream()
                .map(order ->{
                    UserOrderListDto dto = order.toUserOrderListDto(order);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    /**
     * 주문 하기
     */
    @Transactional
    public Order registerOrder(Order order, List<OrderItem> orderList) {

        if (orderList.isEmpty()) {
            throw new CustomException(VARIABLE_ERROR.getCode(), VARIABLE_ERROR.getMessage());
        }

        Order saveOrder = toSaveOrder(order, orderList);
        try {
            return repository.save(saveOrder);
        } catch (EntityExistsException | DataIntegrityViolationException e) {
            throw new IllegalArgumentException();
        } catch (RuntimeException e){
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

            log.info("order={}", order);
            log.info("dto={}", dto);
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
        order.setTotalCount(order.registerTotalAmount(orderList));
        order.setTotalPrice(order.registerTotalPrice(orderList));

        return order;
    }



}
