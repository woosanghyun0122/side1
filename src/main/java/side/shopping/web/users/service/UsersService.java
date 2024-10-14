package side.shopping.web.users.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import side.shopping.domain.users.Users;
import side.shopping.repository.users.UserRepository;
import side.shopping.repository.users.dto.users.LoginDto;
import side.shopping.repository.users.dto.users.LoginResponseDto;
import side.shopping.repository.users.dto.users.PersistUserDto;
import side.shopping.repository.users.dto.users.UpdateUserDto;

import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
public class UsersService {

    @Autowired
    private UserRepository repository;

    /* 로그인 */
    public Users login(LoginDto dto) throws IllegalAccessException {

        log.info("loginSerivce={}", dto);
        return repository.findByUserid(dto.getLoginId())
                .orElseThrow(()-> new NoSuchElementException("존재하지 않는 아이디입니다."));
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
    public Users save(PersistUserDto dto) {

        log.info("serivce.save()");
        Users user = new Users();
        user.saveUser(dto);
        return repository.save(user);
    }

    @Transactional
    public void update(UpdateUserDto dto) {
        Users updateUser = repository.findById(dto.getId())
                .orElseThrow(() -> new NoSuchElementException());
        updateUser.updateUserInfo(dto);
        repository.save(updateUser);
    }

    @Transactional
    public void delete(Long id) {

        repository.deleteById(id);
    }


}
