package side.shopping.web.payment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import side.shopping.cache.CacheService;
import side.shopping.domain.order.Order;
import side.shopping.repository.order.dto.OrderToPayDto;
import side.shopping.web.order.service.OrderService;

@Slf4j
@Controller
@RequestMapping("/payment")
public class PaymentViewController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CacheService cacheService;

    @Value("${payment.toss.test_secrete_api_key}")
    private String clientKey;

    @GetMapping("/checkout")
    public String checkout(@RequestParam("key") String key, Model model) {

        OrderToPayDto order = (OrderToPayDto) cacheService.getCacheValue(key);
        log.info("price={}", order.getTotalPrice());
        model.addAttribute("order", order);
        model.addAttribute("clientKey", clientKey);

        return "/payment/checkout";
    }
}
