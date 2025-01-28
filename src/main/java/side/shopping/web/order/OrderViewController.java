package side.shopping.web.order;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import side.shopping.cache.CacheService;
import side.shopping.config.SessionManager;
import side.shopping.domain.order.*;
import side.shopping.exception.CustomException;
import side.shopping.repository.order.OrderRepository;
import side.shopping.repository.order.dto.OrderItemDto;
import side.shopping.repository.order.dto.UserOrderListDto;
import side.shopping.repository.users.dto.users.LoginResponseDto;
import side.shopping.web.order.service.OrderItemService;
import side.shopping.web.order.service.OrderService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static side.shopping.exception.ErrorCode.*;

@Slf4j
@Controller
@RequestMapping("/order")
public class OrderViewController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService itemService;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private SessionManager sessionManager;


    /**
     * 주문 하기 화면
     */
    @GetMapping("/register/orderList")
    public String register(@RequestParam(name = "key") String key, Model model) {

        log.info("key={}", key);
        List<OrderItemDto> list = (List<OrderItemDto>) cacheService.getCacheValue(key);

        log.info("list={}", list.get(0));

        if (list.isEmpty()) {
            throw new CustomException(SERVER_ERROR.getCode(), SERVER_ERROR.getMessage());
        }

        int totalPrice = list.stream()
                .mapToInt(item -> item.getProductPrice() * item.getAmount())
                .sum();

        model.addAttribute("key", key);
        model.addAttribute("order",new Order());
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("orderList", list);
        model.addAttribute("method", Arrays.asList(Method.values()));

        return "/order/order-register";
    }


    /**
     * 주문 내역 조회 화면
     */
    @GetMapping("/myOrderList")
    public String findOrderList(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession(false);
        LoginResponseDto loginUser = (LoginResponseDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            throw new CustomException(SERVER_ERROR.getCode(), SERVER_ERROR.getMessage());
        }

        String loginId = loginUser.getUserId();
        List<UserOrderListDto> list = orderService.findOrderList(loginId);
        model.addAttribute("orderList", list);

        return "/order/orderList";
    }

    /**
     * 주문 정보 수정 화면
     */
    @GetMapping("/modify")
    public String modifyOrder(@RequestParam(name = "num") String orderNum, Model model) {

        Order order = orderRepository.findById(orderNum)
                .orElseThrow(() -> new CustomException(SELECT_ERROR.getCode(), SELECT_ERROR.getMessage()));

        model.addAttribute("order", order);
        return "/order/modifyOrder";
    }

    /**
     * 교환 팝업
     */
    @GetMapping("/exchange")
    public String exchange(@RequestParam(name = "id")Long id, Model model) {

        OrderItem item = itemService.findById(id);
        model.addAttribute("item", item);
        model.addAttribute("reasons", Reason.values());
        return "/order/exchange";
    }

    /**
     * 환불 팝업
     */
    @GetMapping("/refund")
    public String refund(@RequestParam(name = "id")Long id, Model model) {

        OrderItem item = itemService.findById(id);
        model.addAttribute("item", item);
        model.addAttribute("reasons", Reason.values());
        return "/order/refund";
    }

    /**
     * 교환/환불 반려 팝업
     */
    @GetMapping("/reject")
    public String reject(@RequestParam(name = "id")Long id, Model model) {

        OrderItem item = itemService.findById(id);
        model.addAttribute("item", item);
        model.addAttribute("reasons", RejectReason.values());
        return "/order/reject";
    }



    /**
     *
     * */
    @GetMapping("/requestList")
    public String changeList(Model model) {

        LoginResponseDto loginUser = sessionManager.getLoginUser();

        List<OrderItem> exchangeList = itemService.findExchangeList(loginUser.getUserId());
        List<OrderItem> refundList = itemService.findRefundList(loginUser.getUserId());

        model.addAttribute("exchangeList", exchangeList);
        model.addAttribute("refundList", refundList);

        return "/order/changeList";
    }


}
