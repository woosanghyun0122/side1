package side.shopping.web.users;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import side.shopping.domain.users.Users;
import side.shopping.repository.users.dto.users.FindUserDto;
import side.shopping.repository.users.dto.users.LoginDto;
import side.shopping.repository.users.dto.users.LoginResponseDto;
import side.shopping.repository.users.dto.users.UpdateUserDto;
import side.shopping.web.users.service.UsersService;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserApiController {

    @Autowired
    private UsersService service;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Validated LoginDto dto
            , HttpServletRequest request
            , @RequestParam(value = "redirectURL" , defaultValue = "/") String redirectURL) {

        log.info("redirectURL ={}", redirectURL);

        LoginResponseDto loginUser = service.login(dto);

        // 비밀번호 확인 로직
        HttpSession session = request.getSession(true);

        session.setAttribute("loginUser", loginUser);
        log.info("role={}",loginUser.getRole());

        return ResponseEntity.status(HttpStatus.OK).body(redirectURL);

    }

    @PostMapping("/find-userid")
    public ResponseEntity<String> findUserid(@RequestBody @Validated FindUserDto dto) {

        String findId = service.findUserInfo(dto);
        return ResponseEntity.status(HttpStatus.OK).body(findId);
    }

    @PostMapping("/find-pw")
    public ResponseEntity<String> findPw(@RequestBody @Validated FindUserDto dto) {

        String findPw = service.findUserInfo(dto);
        return ResponseEntity.status(HttpStatus.OK).body(findPw);
    }


    @GetMapping("/check-userid/{userid}")
    public ResponseEntity<?> isExistUserid(@PathVariable("userid") @Validated String userid) {

        boolean isExist = service.isExistUserid(userid);

        if (isExist) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 아이디입니다.");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("사용 가능한 아이디입니다.");
        }

    }

    @GetMapping("/check-nickname/{nickName}")
    public ResponseEntity<?> isExistNickName(@PathVariable("nickName") @Validated String nickName) {

        boolean isExist = service.isExistNickname(nickName);
        log.info("isExist={} nickName={}", isExist, nickName);

        if (isExist) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 닉네임입니다.");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("사용 가능한 닉네임입니다.");
        }

    }

    @PutMapping("/modify")
    public ResponseEntity<?> update(@RequestBody @Validated UpdateUserDto dto, HttpServletRequest request) {

        Users updateUser = service.update(dto);
        log.info("update={}", updateUser.getNickName());

        // 세션 값 수정
        HttpSession session = request.getSession(false);
        LoginResponseDto loginResponseDto = service.sessionManage(updateUser);

        session.setAttribute("loginUser", loginResponseDto);
        log.info("updateNickname={}", loginResponseDto.getNickName());

        return ResponseEntity.status(HttpStatus.OK).body("수정에 성공하였습니다.");
    }

    @PostMapping("/signup")
    public ResponseEntity<?> save(@RequestBody @Validated Users dto, HttpServletRequest request) {

        try {

            boolean check = service.isExistPhone(dto.getPhone());

            if (!check) {
                Users saveUser = service.save(dto);
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("하나의 핸드폰 번호로 중복가입은 불가합니다.");
            }

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 사용자입니다.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("저장에 성공하였습니다.");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id, HttpServletRequest request) {

        if (id == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("탈퇴에 실패하였습니다.");
        }

        log.info("id={}", id);
        service.delete(id);
        HttpSession session = request.getSession(false);
        session.invalidate();
        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 탈퇴되었습니다.");

    }
}
