package side.shopping.web.payment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import side.shopping.cache.CacheService;
import side.shopping.domain.order.Order;
import side.shopping.web.order.service.OrderService;

@Slf4j
@Controller
@RequestMapping("/payment")
public class PaymentViewController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CacheService cacheService;

}
