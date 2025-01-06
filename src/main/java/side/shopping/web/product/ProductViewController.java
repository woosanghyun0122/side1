package side.shopping.web.product;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.support.AbstractMultipartHttpServletRequest;
import side.shopping.config.SessionManager;
import side.shopping.domain.product.Category;
import side.shopping.domain.product.Product;
import side.shopping.domain.users.Role;
import side.shopping.domain.zzim.Zzim;
import side.shopping.exception.CustomException;
import side.shopping.exception.ErrorCode;
import side.shopping.repository.product.dto.FindProductDto;
import side.shopping.repository.product.dto.FindSellerProductDto;
import side.shopping.repository.users.dto.users.LoginResponseDto;
import side.shopping.web.category.CategoryService;
import side.shopping.web.product.service.ProductService;
import side.shopping.web.zzim.service.ZzimService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequestMapping("/product")
@Controller
public class ProductViewController {

    @Autowired
    private ProductService service;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ZzimService zzimService;

    @Autowired
    private SessionManager sessionManager;


    /**
     * 제품 상세보기
     * */
    @GetMapping("/detail/{id}")
    public String productDetail(@PathVariable(name = "id") long id, Model model,HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        Product detail = service.findDetail(id);
        List<Long> idList = new ArrayList<>();

        if(session == null){
            service.viewCountUpdate(id);
        }
        else{
            LoginResponseDto loginUser = (LoginResponseDto) session.getAttribute("loginUser");

            idList = zzimService.findZzimList(loginUser.getUserId()).stream()
                    .map(zzim -> zzim.getProduct().getProductId())
                    .collect(Collectors.toList());

            if (loginUser.getUserId() != detail.getUser().getUserid()) {
                service.viewCountUpdate(id);
            }
        }

        model.addAttribute("detail", detail);
        model.addAttribute("idList", idList);

        return "/product/detail";
    }

    /**
     * 카테고리별 조회
     * */
    @GetMapping("/category/{categoryId}")
    public String categoryList(@PathVariable(name = "categoryId") String categoryId, Model model,HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        List<Long> idList = new ArrayList<>();


        if (session == null) {
            String userid = sessionManager.getLoginUser().getUserId();
            List<Zzim> zzimList = zzimService.findZzimList(userid);
            idList = zzimList.stream()
                    .map(zzim -> zzim.getProduct().getProductId())
                    .collect(Collectors.toList());
        }

        List<Product> list = service.findByCategoryId(categoryId);
        model.addAttribute("list", list);
        return "/product/categoryList";
    }

    /**
     * 카테고리별 조회 상위
     * */
    @GetMapping("/category/top/{parentId}")
    public String parentList(@PathVariable(name = "parentId") String parentId, Model model) {

        List<Product> list = service.findByParentId(parentId);

        List<Category> categories = categoryService.findDepth(1);

        String parentName = categories.stream()
                .filter(name -> name.getCategoryId().equals(parentId))
                .map(Category::getCategoryName)
                .findFirst()
                .orElse(null);

        model.addAttribute("parentName", parentName);
        model.addAttribute("list", list);
        return "/product/categoryList";
    }

    /**
     * 판매자 물건 조회
     * */
    @GetMapping("/seller/list")
    public String sellerProductList(HttpSession session,Model model) {


            LoginResponseDto loginUser = (LoginResponseDto) session.getAttribute("loginUser");
            String sellerId = loginUser.getUserId();
            log.info("seller={}", sellerId);
            List<FindSellerProductDto> list = service.sellerProductList(sellerId);
            model.addAttribute("sellerList", list);
            return "/product/seller-product-list";

    }

    /**
     * 판매 상품 등록
     * */
    @GetMapping("/seller/add")
    public String productAdd(Model model) {

        Product product = new Product();
        model.addAttribute("addProduct", product);

        List<Category> parentList= categoryService.findDepth(1);
        List<Category> lowerList= categoryService.findDepth(2);
        model.addAttribute("parentCategory", parentList);
        model.addAttribute("lowerCategory", lowerList);


        return "/product/product-form";
    }

    /**
     * 판매 상품 수정
     */
    @GetMapping("/seller/modify/{productId}")
    public String productModify(@PathVariable(name = "productId") Long productId, Model model) {

        if (productId == null) {
            throw new CustomException(ErrorCode.VARIABLE_ERROR.getCode(), ErrorCode.VARIABLE_ERROR.getMessage());
        }

        Product product = service.findDetail(productId);
        model.addAttribute("product", product);

        return "/product/productModify";
    }


}
