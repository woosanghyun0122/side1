package side.shopping.web.product;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import side.shopping.domain.product.Category;
import side.shopping.domain.product.Product;
import side.shopping.exception.CustomException;
import side.shopping.exception.ErrorCode;
import side.shopping.repository.product.dto.FindProductDto;
import side.shopping.repository.product.dto.FindSellerProductDto;
import side.shopping.repository.users.dto.users.LoginResponseDto;
import side.shopping.web.category.CategoryService;
import side.shopping.web.product.service.ProductService;

import java.util.List;

@Slf4j
@RequestMapping("/product")
@Controller
public class ProductViewController {

    @Autowired
    private ProductService service;

    @Autowired
    private CategoryService categoryService;


    @GetMapping("/detail/{id}")
    public String productDetail(@PathVariable(name = "id") long id, Model model) {

        Product detail = service.findDetail(id);
        model.addAttribute("detail", detail);

        return "/product/detail";
    }

    @GetMapping("/category/{categoryId}")
    public String categoryList(@PathVariable(name = "categoryId") String categoryId, Model model) {

        List<Product> list = service.findByCategoryId(categoryId);
        model.addAttribute("list", list);
        return "/product/categoryList";
    }

    @GetMapping("/category/top/{parentId}")
    public String parentList(@PathVariable(name = "parentId") String parentId, Model model) {

        List<Product> list = service.findByParentId(parentId);

        List<Category> categories = categoryService.findHighestDepth();

        String parentName = categories.stream()
                .filter(name -> name.getCategoryId().equals(parentId))
                .map(Category::getCategoryName)
                .findFirst()
                .orElse(null);

        model.addAttribute("parentName", parentName);
        model.addAttribute("list", list);
        return "/product/categoryList";
    }

    @GetMapping("/seller/list")
    public String sellerProductList(HttpSession session,Model model) {

        LoginResponseDto loginUser = (LoginResponseDto) session.getAttribute("loginUser");
        String sellerId = loginUser.getUserId();
        log.info("seller={}", sellerId);
        List<FindSellerProductDto> list = service.sellerProductList(sellerId);
        model.addAttribute("sellerList", list);
        return "/product/seller-product-list";
    }

    @GetMapping("/add")
    public String productAdd(Model model) {

        Product product = new Product();
        model.addAttribute("addProduct", product);

        List<Category> list= categoryService.findHighestDepth();
        return "/product/product-form";
    }

    @GetMapping("/modify")
    public String productModify(@PathVariable(name = "productId") Long productId, Model model) {

        if (productId == null) {
            throw new CustomException(ErrorCode.VARIABLE_ERROR.getCode(), ErrorCode.VARIABLE_ERROR.getMessage());
        }

        Product product = service.findDetail(productId);
        model.addAttribute("product", product);

        return "/product/productModify";
    }
}
