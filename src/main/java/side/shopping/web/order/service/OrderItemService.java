package side.shopping.web.order.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import side.shopping.domain.order.OrderItem;
import side.shopping.domain.order.Reason;
import side.shopping.domain.order.RejectReason;
import side.shopping.domain.order.Status;
import side.shopping.domain.product.Product;
import side.shopping.exception.CustomException;
import side.shopping.repository.order.OrderItemRepository;
import side.shopping.repository.order.dto.UpdateOrderItemDto;
import side.shopping.repository.product.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

import static side.shopping.exception.ErrorCode.*;

@Slf4j
@Service
public class OrderItemService {

    @Autowired
    OrderItemRepository repository;

    @Autowired
    ProductRepository productRepository;


    /**
     * 주문 상품 조회
     */
    public OrderItem findById(Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new CustomException(SELECT_ERROR.getCode(), SELECT_ERROR.getMessage()));

    }

    /**
     * 교환 서비스
     * 신청은 무조건 가능
     */
    @Transactional
    public OrderItem exchange(UpdateOrderItemDto dto) {

        OrderItem item = repository.findById(dto.getId())
                .orElseThrow(() -> new CustomException(SELECT_ERROR.getCode(), SERVER_ERROR.getMessage()));

        try {
                item.setStatus(Status.EXCHANGE);
                item.setCancelReason(getReason(dto.getCancelReason()));
                return item;
        } catch (Exception e) {
            log.info("error={}", e);
            throw new CustomException(UPDATE_ERROR.getCode(), UPDATE_ERROR.getMessage());
        }
    }

    /**
     * 환불 서비스
     * 신청은 무조건 가능
     */
    @Transactional
    public OrderItem refund(UpdateOrderItemDto dto) {

        OrderItem item = repository.findById(dto.getId())
                .orElseThrow(() -> new CustomException(SELECT_ERROR.getCode(), SERVER_ERROR.getMessage()));

        try {
                item.setStatus(Status.REFUND);
                item.setCancelReason(getReason(dto.getCancelReason()));
                return item;
        } catch (Exception e) {
            log.info("error={}", e);
            throw new CustomException(UPDATE_ERROR.getCode(), UPDATE_ERROR.getMessage());
        }
    }

    /**
     * 교환 승인 대상 조회
     */
    public List<OrderItem> findExchangeList(String userid) {

        List<OrderItem> list = repository.findExchangeList(userid,Status.EXCHANGE);
        return list;
    }

    /**
     * 환불 승인 대상 조회
     */
    public List<OrderItem> findRefundList(String userid) {

        List<OrderItem> list = repository.findRefundList(userid,Status.REFUND);
        return list;
    }

    /**
     * 교환/환불 승인
     */
    @Transactional
    public OrderItem requestApprove(UpdateOrderItemDto dto) {

        OrderItem item = repository.findById(dto.getId())
                .orElseThrow(() -> new CustomException(SELECT_ERROR.getCode(), SERVER_ERROR.getMessage()));

        try {
            if (item.getStatus().equals(Status.EXCHANGE)) {
                item.setStatus(Status.EXCHANGE_CONFIRM);
            } else if (item.getStatus().equals(Status.REFUND)) {
                item.setStatus(Status.REFUND_CONFIRM);
            }
            return item;
        } catch (Exception e) {
            log.info("error={}", e);
            throw new CustomException(UPDATE_ERROR.getCode(), UPDATE_ERROR.getMessage());
        }
    }

    /**
     * 교환/환불 거절
     */
    @Transactional
    public OrderItem requestDenied(UpdateOrderItemDto dto) {

        OrderItem item = repository.findById(dto.getId())
                .orElseThrow(() -> new CustomException(SELECT_ERROR.getCode(), SERVER_ERROR.getMessage()));

        try {
            if (item.getStatus().equals(Status.EXCHANGE)) {
                item.setStatus(Status.EXCHANGE_DENIED);
                item.setRejectReason(getRejectReason(dto.getRejectReason()));
            } else if (item.getStatus().equals(Status.REFUND)) {
                item.setStatus(Status.REFUND_DENIED);
                item.setRejectReason(getRejectReason(dto.getRejectReason()));
            }
            return item;
        } catch (Exception e) {
            log.info("error={}", e);
            throw new CustomException(UPDATE_ERROR.getCode(), UPDATE_ERROR.getMessage());
        }
    }


    /**
     *  재고 확인 함수
     * */
    private Boolean checkAmount(Long productId, int amount) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(SELECT_ERROR.getCode(), SERVER_ERROR.getMessage()));

        if (product.getQuantity() >= amount) {
            return true;
        } else {
            return false;
        }
    }

    private Reason getReason(String value) {

        Reason[] reasons = Reason.values();
        int i=0;

        for (i=0; i< reasons.length; i ++){

            if (reasons[i].getValue().equals(value)) {
                return reasons[i];
            }
        }
        return reasons[i];
    }

    private RejectReason getRejectReason(String value) {

        RejectReason[] reasons = RejectReason.values();
        int i=0;

        for (i=0; i< reasons.length; i ++){

            if (reasons[i].getValue().equals(value)) {
                return reasons[i];
            }
        }
        return reasons[i];
    }





}
