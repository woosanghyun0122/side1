package side.shopping.web.product;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.session.StandardSessionFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import side.shopping.config.SessionManager;
import side.shopping.domain.product.Product;
import side.shopping.domain.zzim.Zzim;
import side.shopping.repository.product.dto.FindProductDto;
import side.shopping.web.product.service.ProductService;
import side.shopping.web.zzim.service.ZzimService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
public class HomeController {

    @Autowired
    private ProductService service;

    @Autowired
    private SessionManager sessionManager;

    @Autowired
    private ZzimService zzimService;

    @GetMapping("/")
    public String home(Model model, HttpServletRequest request) {

        // 판매순 top5
        List<FindProductDto> topList = service.findTop();

        // 최신순 top5
        List<FindProductDto> recentList = service.findRecently();

        // 찜 리스트
        List<Long> idList = new ArrayList<>();

        HttpSession session = request.getSession(false);

        if (session != null) {
            String userid = sessionManager.getLoginUser().getUserId();
            List<Zzim> zzimList = zzimService.findZzimList(userid);
            idList = zzimList.stream()
                    .map(zzim -> zzim.getProduct().getProductId())
                    .collect(Collectors.toList());
        }

        model.addAttribute("TopList", topList);
        model.addAttribute("RecentList", recentList);
        model.addAttribute("idList", idList);


        return "index";
    }
}
