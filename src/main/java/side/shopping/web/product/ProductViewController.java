package side.shopping.web.product;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import side.shopping.domain.product.Category;
import side.shopping.domain.product.Product;
import side.shopping.repository.product.dto.FindProductDto;
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

}
