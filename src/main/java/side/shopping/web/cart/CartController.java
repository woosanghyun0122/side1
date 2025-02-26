package side.shopping.web.cart;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import side.shopping.domain.cart.Cart;
import side.shopping.exception.CustomException;
import side.shopping.exception.ErrorCode;
import side.shopping.repository.cart.dto.CartDto;
import side.shopping.repository.users.dto.users.LoginResponseDto;
import side.shopping.web.cart.service.CartService;

import java.util.List;

import static side.shopping.exception.ErrorCode.*;

@Slf4j
@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/cart")
    public String myCartList(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession(false);
        LoginResponseDto loginUser = (LoginResponseDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            throw new CustomException(SERVER_ERROR.getCode(), SERVER_ERROR.getMessage());
        }

        List<Cart> cart = cartService.findCartList(loginUser.getUserId());
        model.addAttribute("list", cart);

        return "cart/cartList";
    }

    @PostMapping("/cart")
    public ResponseEntity saveCart(@RequestBody CartDto dto, HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        LoginResponseDto loginUser = (LoginResponseDto) session.getAttribute("loginUser");

        cartService.saveCart(dto, loginUser.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @DeleteMapping("/cart")
    public ResponseEntity deleteCart(@RequestBody List<Long> list) {

        cartService.deleteCart(list);
        return ResponseEntity.status(HttpStatus.OK).body("삭제되었습니다.");
    }
}
