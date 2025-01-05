package side.shopping.web.product;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import side.shopping.config.SessionManager;
import side.shopping.domain.zzim.Zzim;
import side.shopping.repository.product.dto.FindProductDto;
import side.shopping.web.product.service.ProductService;
import side.shopping.web.zzim.service.ZzimService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
public class HomeController {

    @Autowired
    ProductService service;

    @Autowired
    private ZzimService zzimService;

    @Autowired
    private SessionManager sessionManager;

    @GetMapping("/")
    public String home(Model model, HttpServletRequest request) {

        // 판매순 top5
        List<FindProductDto> topList = service.findTop();

        // 최신순 top5
        List<FindProductDto> recentList = service.findRecently();

        HttpSession session = request.getSession(false);

        if (session != null) {
            String userid = sessionManager.getLoginUser().getUserId();
            List<Zzim> zzimList = zzimService.findZzimList(userid);

            for (Zzim zzim : zzimList){

                topList.stream()
                        .filter(top -> top.getProductId() == zzim.getProduct().getProductId())
                        .forEach(top -> top.setZzim(true));

                recentList.stream()
                        .filter(recent -> recent.getProductId() == zzim.getProduct().getProductId())
                        .forEach(recent -> recent.setZzim(true));
            }

        }

        model.addAttribute("TopList", topList);
        model.addAttribute("RecentList", recentList);


        return "index";
    }
}
