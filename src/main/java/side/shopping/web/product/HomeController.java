package side.shopping.web.product;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import side.shopping.repository.product.dto.FindProductDto;
import side.shopping.web.product.service.ProductService;

import java.util.List;

@Slf4j
@Controller
public class HomeController {

    @Autowired
    ProductService service;

    @GetMapping("/")
    public String home(Model model) {

        // 판매순 top5
        List<FindProductDto> topList = service.findTop();

        // 최신순 top5
        List<FindProductDto> recentList = service.findRecently();

        model.addAttribute("TopList", topList);
        model.addAttribute("RecentList", recentList);


        return "index";
    }
}
