package side.shopping.web.users;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import side.shopping.domain.users.Users;
import side.shopping.repository.users.dto.users.LoginDto;
import side.shopping.repository.users.dto.users.LoginResponseDto;
import side.shopping.web.users.service.UsersService;

import javax.naming.AuthenticationException;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserApiController {

    @Autowired
    private UsersService service;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated @ModelAttribute("loginDto") LoginDto dto, BindingResult bindingResult, HttpServletRequest request) {

        log.info("bindingResult ={}", bindingResult.getFieldErrors());

        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        try {
            Users loginUser = service.login(dto);

            // 비밀번호 확인 로직
            if ((loginUser != null) && dto.getPassword().equals(loginUser.getPassword())) {
                HttpSession session = request.getSession();

                LoginResponseDto loginResponseDto = LoginResponseDto.builder()
                                        .userId(loginUser.getUserid())
                                        .userName(loginUser.getUserName())
                                        .nickName(loginUser.getNickName())
                                        .role(loginUser.getRole())
                                                .build();

                session.setAttribute("loginUser", loginResponseDto);

            } else if (loginUser == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("아이디가 존재하지 않습니다.");

            } else if(!(dto.getPassword().equals(loginUser.getPassword()))) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("비밀번호가 잘못되었습니다.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body("알 수 없는 오류가 발생하였습니다.");
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
