package side.shopping.web.admin;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import side.shopping.config.SessionManager;
import side.shopping.repository.admin.dto.*;
import side.shopping.web.admin.service.AdminService;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

@Slf4j
@Controller
public class AdminController {

    @Autowired
    private SessionManager sessionManager;

    @Autowired
    private AdminService service;


    @GetMapping("/admin")
    public String adminHome(Model model) {

        List<UserListDto> userList = new ArrayList<>();
        List<ProductListDto> productList = new ArrayList<>();
        List<OrderListDto> orderList = new ArrayList<>();
        List<PaymentListDto> paymentList = new ArrayList<>();
        AdminConditionDto dto = new AdminConditionDto();


        model.addAttribute("userList", userList);
        model.addAttribute("productList", productList);
        model.addAttribute("orderList", orderList);
        model.addAttribute("paymentList", paymentList);
        model.addAttribute("entity", dto.getEntity());
        model.addAttribute("dto", dto);

        return "/admin";
    }

    @GetMapping("/admin/search")
    public String search(Model model,
                         @RequestParam(name = "page", defaultValue = "0") int page,
                         @RequestParam(name="size",defaultValue = "10") int size,
                         @RequestParam(name = "entity") String entity,
                         @RequestParam(name = "userid") String userid,
                         @RequestParam(name = "orderNum") String orderNum,
                         @RequestParam(name = "startDate") String startDate,
                         @RequestParam(name = "endDate")String endDate
                         , HttpServletRequest request
                         ){


        Pageable pageable = PageRequest.of(page, size);

        AdminConditionDto dto = AdminConditionDto.builder()
                .userid(userid)
                .entity(entity)
                .orderNum(orderNum)
                .startDate(startDate)
                .endDate(endDate)
                .build();

        model.addAttribute("dto", dto);
        model.addAttribute("httpServletRequest", request);
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);

        Page<?> list;

        if (dto.getEntity().equals("user")) {
            log.info("startDate={},endDate={}", startDate, endDate);
            list = service.findUser(pageable, dto);
        } else if (dto.getEntity().equals("product")) {
            list = service.findProduct(pageable, dto);
        } else if (dto.getEntity().equals("order")) {
            list = service.findOrder(pageable, dto);
        } else {
            list = service.findAllPayment(pageable, dto);
        }

        model.addAttribute("results", list.getContent());
        model.addAttribute("entity", dto.getEntity());
        model.addAttribute("totalElements", list.getTotalElements());
        model.addAttribute("endPage", list.getTotalPages()); // 전체 페이지 수가 0부터 시작해서 -1

        log.info("endPage={}", list.getTotalPages());
        log.info("totalElements={}", list.getTotalElements());


        return "/admin";
    }
}
