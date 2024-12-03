package side.shopping.web.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import side.shopping.domain.order.OrderItem;
import side.shopping.exception.CustomException;
import side.shopping.exception.ErrorCode;
import side.shopping.repository.order.OrderItemRepository;
import side.shopping.repository.order.dto.FindOrderItemDto;
import side.shopping.repository.order.dto.UpdateOrderItemDto;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static side.shopping.exception.ErrorCode.*;

@Service
public class OrderItemService {

    @Autowired
    OrderItemRepository repository;


    /**
     * 주문 내역 조회
     */

    public List<FindOrderItemDto> findOrderItemList(String orderNum) {

        if (!StringUtils.hasText(orderNum)) {
            throw new CustomException(VARIABLE_ERROR.getCode(), VARIABLE_ERROR.getMessage());
        }

        List<OrderItem> list = repository.findByOrder_OrderNum(orderNum);

        if (list.isEmpty()) {
            throw new CustomException(SELECT_ERROR.getCode(), SERVER_ERROR.getMessage());
        }

        return list.stream()
                .map(item -> {
                    FindOrderItemDto dto = FindOrderItemDto.builder()
                            .productId(item.getProduct().getProductId())
                            .productName(item.getProduct().getName())
                            .productPrice(item.getProduct().getPrice())
                            .amount(item.getAmount())
                            .status(item.getStatus())
                            .orderDate(item.getOrder().getOrderDate())
                            .build();
                    return dto;
                })
                .collect(Collectors.toList());

    }


    /**
     * 주문 상태 변경 status
     * 환불신청 , 교환신청 -> role에 상관없이
     * 환불 승인, 교환 승인 -> SELLER만
     * 배송관련은 api가 필요하지 않을까?
     */
    public OrderItem modifyOrderItem(UpdateOrderItemDto dto) {

        try {
            OrderItem orderItem = repository.findById(dto.getId())
                    .orElseThrow(() -> new CustomException(SELECT_ERROR.getCode(), SELECT_ERROR.getMessage()));

             orderItem.toOrderItem(dto);
            return orderItem;
        } catch (Exception e) {
            throw new CustomException(UPDATE_ERROR.getCode(), UPDATE_ERROR.getMessage());
        }
    }





}
