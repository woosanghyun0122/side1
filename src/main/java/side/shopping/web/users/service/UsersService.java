package side.shopping.web.users.service;

import jakarta.persistence.EntityExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import side.shopping.domain.users.Users;
import side.shopping.repository.users.UserRepository;
import side.shopping.repository.users.dto.users.LoginDto;
import side.shopping.repository.users.dto.users.LoginResponseDto;
import side.shopping.repository.users.dto.users.PersistUserDto;
import side.shopping.repository.users.dto.users.UpdateUserDto;

import javax.swing.text.html.parser.Entity;
import java.security.InvalidParameterException;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
public class UsersService {

    @Autowired
    private UserRepository repository;

    /* 로그인 */
    public LoginResponseDto login(LoginDto dto) throws IllegalAccessException {

        log.info("loginId={}", dto.getLoginId());

        Users loginUser = repository.findByUserid(dto.getLoginId())
                .orElseThrow(() -> new NoSuchElementException("아이디가 존재하지 않습니다."));

        if (!loginUser.getPassword().equals(dto.getPassword())) {
            throw new NoSuchElementException("비밀번호 잘못되었습니다.");
        }

        return LoginResponseDto.builder()
                .userId(loginUser.getUserid())
                .userName(loginUser.getUserName())
                .nickName(loginUser.getNickName())
                .role(loginUser.getRole())
                .build();
    }

    // 아이디 중복 체크 여부
    public Boolean isExistUserid(String userid) {
        log.info("isExistUserid");
        return repository.existsByUserid(userid);
    }

    // 닉네임 중복 체크
    public Boolean isExistNickname(String nickname) {

        return repository.existsByNickName(nickname);
    }

    // 회원가입
    @Transactional
    public Users save(PersistUserDto dto) throws JpaSystemException {

        try {
            return repository.save(dto);
        } catch (EntityExistsException | DataIntegrityViolationException e) {
            throw new IllegalArgumentException();
        } catch (Exception e) {
            throw new JpaSystemException((RuntimeException) e);
        }
    }

    @Transactional
    public Users update(UpdateUserDto dto) {
        Users updateUser = repository.findById(dto.getId())
                .orElseThrow(() -> new NoSuchElementException("잘못된 접근입니다."));
        updateUser.updateUserInfo(dto);
        return repository.save(updateUser);
    }

    @Transactional
    public void delete(Long id) {

        repository.deleteById(id);
    }

    public LoginResponseDto sessionManage(Users users) {

        return LoginResponseDto.builder()
                .userId(users.getUserid())
                .userName(users.getUserName())
                .nickName(users.getNickName())
                .role(users.getRole())
                .build();
    }


}
