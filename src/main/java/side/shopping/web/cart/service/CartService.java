package side.shopping.web.cart.service;

import jakarta.persistence.EntityExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import side.shopping.domain.cart.Cart;
import side.shopping.domain.product.Product;
import side.shopping.domain.users.Users;
import side.shopping.exception.CustomException;
import side.shopping.repository.cart.CartRepository;
import side.shopping.repository.cart.dto.CartDto;
import side.shopping.repository.order.dto.OrderItemDto;
import side.shopping.repository.product.ProductRepository;
import side.shopping.repository.users.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static side.shopping.exception.ErrorCode.*;

@Slf4j
@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;


    /**
     * 회원별 장바구니 목록 조회
     */
    public List<Cart> findCartList(String userid) {

        List<Cart> myList = cartRepository.findByUser_Userid(userid);

        return myList;
    }

    /**
     * 선택된 장바구니 상품 주문화면에서 조회
     */
    public List<OrderItemDto> cartToOrderList(List<Long> list) {

        List<Cart> cartList = cartRepository.findByCartList(list);

        if (cartList.isEmpty()) {
            throw new CustomException(SELECT_ERROR.getCode(), SERVER_ERROR.getMessage());
        }

        return cartList.stream()
                .map(item ->
                {
                    return OrderItemDto.builder()
                            .productId(item.getProduct().getProductId())
                            .productName(item.getProduct().getName())
                            .productPrice(item.getProduct().getPrice())
                            .amount(item.getAmount())
                            .build();
                }).collect(Collectors.toList());

    }


    /**
     * 장바구니 저장
     */
    @Transactional
    public Cart saveCart(CartDto dto, String userid) {

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new CustomException(SELECT_ERROR.getCode(), SELECT_ERROR.getMessage()));

        Users user = userRepository.findByUserid(userid)
                .orElseThrow(() -> new CustomException(SELECT_ERROR.getCode(), SERVER_ERROR.getMessage()));

        Cart cart = Cart.builder()
                .user(user)
                .product(product)
                .amount(dto.getAmount())
                .build();

        return cartRepository.save(cart);
    }

    /**
     * 장바구니에서 삭제
     */
    @Transactional
    public void deleteCart(List<Long> list) {

        try {
            if (!list.isEmpty()) {
                cartRepository.deleteProduct(list);
            }
        } catch (Exception e) {
            log.info("error={}", e);
            throw new CustomException(DELETE_ERROR.getCode(), DELETE_ERROR.getMessage());
        }
    }

}
