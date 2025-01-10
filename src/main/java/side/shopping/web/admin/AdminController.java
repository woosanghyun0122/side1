package side.shopping.web.admin;

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

    @ResponseBody
    @GetMapping("/admin/search")
    public ResponseEntity search(Model model,
                                 @RequestParam(name = "page", defaultValue = "0") int page,
                                 @RequestParam(name="size",defaultValue = "10") int size,
                                 @RequestParam(name = "entity") String entity,
                                 @RequestParam(name = "userid") String userid,
                                 @RequestParam(name = "orderNum") String orderNum,
                                 @RequestParam(name = "startDate") LocalDate startDate,
                                 @RequestParam(name = "endDate")LocalDate endDate
                                 ){

        Pageable pageable = PageRequest.of(page, size);

        AdminConditionDto dto = AdminConditionDto.builder()
                .userid(userid)
                .entity(entity)
                .orderNum(orderNum)
                .startDate(startDate)
                .endDate(endDate)
                .build();

        if (dto.getEntity().equals("user")) {
            Page<UserListDto> list = service.findUser(pageable, dto);
            model.addAttribute("results", list);
            model.addAttribute("entity", dto.getEntity());
            model.addAttribute("totalElements", list.getTotalElements());
        } else if (dto.getEntity().equals("product")) {
            Page<ProductListDto> list = service.findProduct(pageable, dto);
            model.addAttribute("results", list);
            model.addAttribute("entity", dto.getEntity());
            model.addAttribute("totalElements", list.getTotalElements());

        } else if (dto.getEntity().equals("order")) {
            Page<OrderListDto> list = service.findOrder(pageable, dto);
            model.addAttribute("results", list);
            model.addAttribute("entity", dto.getEntity());
            model.addAttribute("totalElements", list.getTotalElements());
        } else {
            Page<PaymentListDto> list = service.findAllPayment(pageable, dto);
            model.addAttribute("results", list);
            model.addAttribute("entity", dto.getEntity());
            model.addAttribute("totalElements", list.getTotalElements());
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
