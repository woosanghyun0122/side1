package side.shopping.web.product;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import side.shopping.domain.product.Category;
import side.shopping.domain.product.Product;
import side.shopping.domain.users.Users;
import side.shopping.repository.category.CategoryRepository;
import side.shopping.repository.product.ProductRepository;
import side.shopping.repository.product.dto.FindProductDto;
import side.shopping.repository.product.dto.UpdateProductDto;
import side.shopping.repository.users.UserRepository;
import side.shopping.web.product.service.ProductService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService service;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository repository;

    @Autowired
    PlatformTransactionManager transactionManager;
    TransactionStatus status;


    @BeforeEach
    void beforeEach() {
        status = transactionManager.getTransaction(new DefaultTransactionDefinition());
    }

    @AfterEach
    void afterEach() {
        transactionManager.rollback(status);
    }

    @Test
    void findTop5ByOrderBySaleCountDescViewCountDesc() {

        List<FindProductDto> list = service.findTop();
        assertThat(list.get(0).getName()).isEqualTo("War and Peace");

    }

    @Test
    void findByProductId() {

        List<FindProductDto> list1 = service.findTop();
        Long id = list1.get(0).getProductId();
        Product product = service.findDetail(id);

        assertThat(product.getName()).isEqualTo(list1.get(0).getName());
    }

    @Test
    void findByCategoryId() {

        List<Product> list = service.findByCategoryId("CAT00101");

        assertThat(list.size()).isEqualTo(10);
    }

    @Test
    void save() {

        Product product = settingProduct();
        Product saveProduct = service.save(product);

        Optional<Product> findProduct = repository.findById(product.getProductId());
        assertThat(findProduct).isNotNull();
        assertThat(findProduct.get().getName()).isEqualTo(saveProduct.getName());

    }

    @Test
    void update() {

        Product product = settingProduct();
        Product saveProduct = service.save(product);

        UpdateProductDto dto = UpdateProductDto
                .builder()
                .productId(saveProduct.getProductId())
                .price(saveProduct.getPrice())
                .name("아무거나")
                .quantity(saveProduct.getQuantity())
                .content(saveProduct.getContent()).build();

        service.update(dto);
        Optional<Product> test = repository.findById(saveProduct.getProductId());


        assertThat(test.get().getName()).isEqualTo("아무거나");
    }

    @Test
    void delete() {

        Product product = settingProduct();
        Product saveProduct = service.save(product);

        service.delete(saveProduct.getProductId());
        Optional<Product> result = repository.findById(saveProduct.getProductId());

        assertThat(result).isEmpty();
    }


    Product settingProduct() {

        Users user = userRepository.findByUserid("seller3")
                .orElseThrow(() -> new NoSuchElementException());



        Category category = categoryRepository.findById("CAT001")
                    .orElseThrow(() -> new NoSuchElementException());

        Product product = Product.builder()
                .name("test1234")
                .price(100000)
                .user(user)
                .content("테스트입니다")
                .category(category)
                .quantity(100)
                .viewCount(10)
                .saleCount(10)
                .build();

        return product;
    }
}