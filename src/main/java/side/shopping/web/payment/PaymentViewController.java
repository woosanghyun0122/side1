package side.shopping.web.payment;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import side.shopping.cache.CacheService;
import side.shopping.domain.order.Order;
import side.shopping.repository.order.dto.OrderToPayDto;
import side.shopping.repository.payment.dto.PaymentResDto;
import side.shopping.repository.users.dto.users.LoginResponseDto;
import side.shopping.web.order.service.OrderService;
import side.shopping.web.payment.service.PaymentService;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/payment")
public class PaymentViewController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private CacheService cacheService;

    @Value("${payment.toss.test_secrete_api_key}")
    private String clientKey;

    @GetMapping("/checkout")
    public String checkout(@RequestParam("key") String key, Model model) {

        OrderToPayDto order = (OrderToPayDto) cacheService.getCacheValue(key);
        model.addAttribute("key", key);
        model.addAttribute("order", order);
        model.addAttribute("clientKey", clientKey);

        return "/payment/checkout";
    }

    @GetMapping("/success")
    public String success(@RequestParam("orderId") String orderId
            , @RequestParam("paymentKey")String paymentKey
            , @RequestParam("amount")int amount
            , Model model
            , HttpServletRequest request
    ) {

        HttpSession session = request.getSession(false);
        LoginResponseDto loginUser = (LoginResponseDto) session.getAttribute("loginUser");

        PaymentResDto result = paymentService.tossPaymentSuccess(paymentKey,orderId,amount,loginUser);
        model.addAttribute("amount", amount);
        model.addAttribute("result", result);
        model.addAttribute("paymentKey", paymentKey);
        return "/payment/success";
    }
}
