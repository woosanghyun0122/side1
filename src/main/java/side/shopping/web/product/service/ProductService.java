package side.shopping.web.product.service;


import jakarta.persistence.EntityExistsException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import side.shopping.domain.order.OrderItem;
import side.shopping.domain.product.Category;
import side.shopping.domain.product.Product;
import side.shopping.domain.users.Users;
import side.shopping.exception.CustomException;
import side.shopping.repository.category.CategoryRepository;
import side.shopping.repository.order.dto.FindOrderItemDto;
import side.shopping.repository.product.ProductRepository;
import side.shopping.repository.product.dto.FindProductDto;
import side.shopping.repository.product.dto.FindSellerProductDto;
import side.shopping.repository.product.dto.SaveProductDto;
import side.shopping.repository.product.dto.UpdateProductDto;
import side.shopping.repository.users.UserRepository;
import side.shopping.repository.users.dto.users.LoginResponseDto;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import static side.shopping.exception.ErrorCode.*;

@Slf4j
@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;


    /**
     * 판매량 순 동일 시 조회수 순 5개
     */
    public List<FindProductDto> findTop() {

        List<Product> list = repository.findTop5ByOrderBySaleCountDescViewCountDesc();

        if (list == null) {
            throw new CustomException(SERVER_ERROR.getCode(), SERVER_ERROR.getMessage());
        }

        return list.stream()
                .map(product -> {
                    FindProductDto dto = new FindProductDto();
                    dto.setProductId(product.getProductId());
                    dto.setPrice(product.getPrice());
                    dto.setName(product.getName());
                    return dto;
                })
                .collect(Collectors.toList());

    }

    /**
     * 최신등록순 순 동일 시 조회수 순 5개
     */
    public List<FindProductDto> findRecently() {

        List<Product> list = repository.findTop5ByOrderByCreatedAtDesc();

        if (list == null) {
            throw new CustomException(SERVER_ERROR.getCode(), SERVER_ERROR.getMessage());
        }

        return list.stream()
                .map(product -> {
                    FindProductDto dto = new FindProductDto();
                    dto.setProductId(product.getProductId());
                    dto.setPrice(product.getPrice());
                    dto.setName(product.getName());
                    return dto;
                })
                .collect(Collectors.toList());

    }


    /**
     * 상세조회
     */
    public Product findDetail(Long id) {

        if (id == null) {
            throw new CustomException(VARIABLE_ERROR.getCode(), VARIABLE_ERROR.getMessage());
        }

        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 상품입니다."));

    }

    /**
     * 조회 수 업데이트
     */
    public void viewCountUpdate(Long productId) {

        try {
            repository.updateViews(productId);
        } catch (Exception e) {
            throw new CustomException(UPDATE_ERROR.getCode(), UPDATE_ERROR.getMessage());
        }
    }

    /**
     * 조회 수 업데이트
     */
    public void saleCountUpdate(Long productId) {


        Product product = repository.findById(productId)
                .orElseThrow(() -> new CustomException(SERVER_ERROR.getCode(), SERVER_ERROR.getMessage()));

        if (product.getQuantity() == 0) {
            throw new CustomException(NO_QUANTITY.getCode(), NO_QUANTITY.getMessage());
        }

        try {
            repository.updateSaleCount(productId);
        } catch (Exception e) {
            throw new CustomException(UPDATE_ERROR.getCode(), UPDATE_ERROR.getMessage());
        }
    }

    /**
     * 카테고리별 조회
     */
    public List<Product> findByCategoryId(String category) {

        if (!StringUtils.hasText(category)) {
            throw new CustomException(VARIABLE_ERROR.getCode(), VARIABLE_ERROR.getMessage());
        }

        List<Product> list = repository.findByCategory_CategoryId(category);

        if (list == null) {
            throw new CustomException(SERVER_ERROR.getCode(), SERVER_ERROR.getMessage());
        }

        return list;
    }

    /**
     * 카테고리별 조회
     */
    public List<Product> findByParentId(String parentId) {

        if (!StringUtils.hasText(parentId)) {
            throw new CustomException(VARIABLE_ERROR.getCode(), VARIABLE_ERROR.getMessage());
        }

        List<Product> list = repository.findByCategory_ParentId(parentId);

        if (list == null) {
            throw new CustomException(SERVER_ERROR.getCode(), SERVER_ERROR.getMessage());
        }

        return list;
    }

    /**
     * 판매자 상품 조회
     */
    public List<FindSellerProductDto> sellerProductList(String userid) {

        if (!StringUtils.hasText(userid)) {
            throw new CustomException(VARIABLE_ERROR.getCode(), VARIABLE_ERROR.getMessage());
        }

        List<Product> list = repository.findByUser_Userid(userid);

        if (list == null) {
            throw new CustomException(SERVER_ERROR.getCode(), SERVER_ERROR.getMessage());
        }

        return list.stream()
                .map(product -> {
                    FindSellerProductDto dto = new FindSellerProductDto();
                    dto.setProductId(product.getProductId());
                    dto.setName(product.getName());
                    dto.setPrice(product.getPrice());
                    dto.setQuantity(product.getQuantity());
                    dto.setSaleCount(product.getSaleCount());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    /**
     * 상품 등록
     */
    @Transactional
    public Product save(SaveProductDto dto, LoginResponseDto loginUser) {

        try {

            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() ->new CustomException(SELECT_ERROR.getCode(), SELECT_ERROR.getMessage()));

            Users user = userRepository.findByUserid(loginUser.getUserId())
                    .orElseThrow(()->new CustomException(SELECT_ERROR.getCode(), SELECT_ERROR.getMessage()));


            Product product = new Product();
            product = product.saveDto(dto,category,user);

            return repository.save(product);

        } catch (EntityExistsException | DataIntegrityViolationException e) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * 상품 정보 수정
     */
    @Transactional
    public void update(UpdateProductDto dto) {

            log.info("productId={}", dto.getProductId());
        try {
            Product product = repository.findById(dto.getProductId())
                    .orElseThrow(() -> new CustomException(SERVER_ERROR.getCode(), SERVER_ERROR.getMessage()));

            product.modify(dto);
            repository.save(product);

        } catch (Exception e) {
            throw new CustomException(UPDATE_ERROR.getCode(), UPDATE_ERROR.getMessage());
        }

    }

    /**
     * 상품 삭제
     * */
    @Transactional
    public void delete(Long id) {
        try {
            repository.deleteByProductId(id);
        } catch (Exception e) {
            throw new CustomException(DELETE_ERROR.getCode(), DELETE_ERROR.getMessage());
        }
    }


}
