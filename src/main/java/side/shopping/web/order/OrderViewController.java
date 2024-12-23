package side.shopping.web.order;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import side.shopping.cache.CacheService;
import side.shopping.domain.Address;
import side.shopping.domain.order.Method;
import side.shopping.domain.order.Order;
import side.shopping.domain.order.OrderItem;
import side.shopping.exception.CustomException;
import side.shopping.exception.ErrorCode;
import side.shopping.repository.order.dto.FindOrderItemDto;
import side.shopping.repository.order.dto.OrderItemDto;
import side.shopping.repository.order.dto.UserOrderListDto;
import side.shopping.repository.product.dto.FindProductDto;
import side.shopping.repository.users.dto.users.LoginResponseDto;
import side.shopping.web.order.service.OrderItemService;
import side.shopping.web.order.service.OrderService;
import side.shopping.web.product.service.ProductService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static side.shopping.exception.ErrorCode.*;

@Slf4j
@Controller
@RequestMapping("/order")
public class OrderViewController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService itemService;

    @Autowired
    private CacheService cacheService;


    /**
     * 주문 하기 화면
     */
    @GetMapping("/register/orderList")
    public String register(@RequestParam(name = "key") String key, Model model) {

        log.info("key={}", key);
        List<OrderItemDto> list = (List<OrderItemDto>) cacheService.getCacheValue(key);

        log.info("productName={}, productPrice={}", list.get(0).getProductName(), list.get(0).getProductPrice());

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
        List<Order> list = orderService.findOrderList(loginId);
        model.addAttribute("orderList", list);

        return "/order/orderList";
    }

    @GetMapping("/modifyOrder/{orderNum}")
    public String modifyOrder(@PathVariable("orderNum") String orderNum, Model model) {

        if (!StringUtils.hasText(orderNum)) {
            throw new CustomException(VARIABLE_ERROR.getCode(), VARIABLE_ERROR.getMessage());
        }

        List<FindOrderItemDto> orderList = itemService.findOrderItemList(orderNum);
        model.addAttribute("orderList", orderList);
        return "/order/modifyOrder";
    }
}
