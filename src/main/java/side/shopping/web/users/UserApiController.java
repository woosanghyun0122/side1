package side.shopping.web.users;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import side.shopping.domain.users.Users;
import side.shopping.repository.users.dto.users.LoginDto;
import side.shopping.repository.users.dto.users.LoginResponseDto;
import side.shopping.repository.users.dto.users.UpdateUserDto;
import side.shopping.web.users.service.UsersService;

import javax.naming.AuthenticationException;
import java.security.InvalidParameterException;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserApiController {

    @Autowired
    private UsersService service;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Validated LoginDto dto, HttpServletRequest request) {

        try {
            LoginResponseDto loginUser = service.login(dto);

            // 비밀번호 확인 로직
                HttpSession session = request.getSession(true);

                session.setAttribute("loginUser", loginUser);
                log.info("session={}", session.getAttribute("loginUser"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body("알 수 없는 오류가 발생하였습니다.");
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/isExistNickName")
    public ResponseEntity<?> isExistNickName(@RequestParam("nickName") String nickName) {

        try {
            boolean isExist = service.isExistNickname(nickName);
            log.info("isExist={} nickName={}", isExist,nickName);

            if (isExist) {
                return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body("이미 존재하는 닉네임입니다.");
            } else {
                return ResponseEntity.status(HttpStatus.OK).body("사용 가능한 닉네임입니다.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("오류가 발생하였습니다.");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody @Validated UpdateUserDto dto, HttpServletRequest request) {

        try {
            Users updateUser = service.update(dto);
            log.info("update={}", updateUser.getNickName());

            HttpSession session = request.getSession(false);
            LoginResponseDto loginResponseDto = service.sessionManage(updateUser);

            session.setAttribute("loginUser", loginResponseDto);
            log.info("updateNickname={}", loginResponseDto.getNickName());

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("알수없는 오류가 발생하였습니다.");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body("개인 정보 수정에 실패하였습니다.");
        }
        return ResponseEntity.status(HttpStatus.OK).body("수정에 성공하였습니다.");
    }
}
