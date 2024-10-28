package side.shopping.web.users.service;

import jakarta.persistence.EntityExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import side.shopping.domain.users.Users;
import side.shopping.exception.CustomException;
import side.shopping.repository.users.UserRepository;
import side.shopping.repository.users.dto.users.FindUserDto;
import side.shopping.repository.users.dto.users.LoginDto;
import side.shopping.repository.users.dto.users.LoginResponseDto;
import side.shopping.repository.users.dto.users.UpdateUserDto;

import java.util.NoSuchElementException;

import static side.shopping.exception.ErrorCode.*;

@Slf4j
@Service
public class UsersService {

    @Autowired
    private UserRepository repository;

    /* 로그인 */
    public LoginResponseDto login(LoginDto dto) {

        log.info("loginId={}", dto.getLoginId());

            Users loginUser = repository.findByUserid(dto.getLoginId())
                    .orElseThrow(() -> new CustomException(INVALID_USERID.getCode(), INVALID_USERID.getMessage()));

            if (!loginUser.getPassword().equals(dto.getPassword())) {
                log.info("비밀번호 오류");
                throw new CustomException(INVALID_PASSWORD.getCode(), INVALID_PASSWORD.getMessage());
            }

            return LoginResponseDto.builder()
                    .userId(loginUser.getUserid())
                    .userName(loginUser.getUserName())
                    .nickName(loginUser.getNickName())
                    .role(loginUser.getRole())
                    .build();

    }

    // 아이디 중복 체크 여부
    public Boolean isExistUserid(String userid){
        if (!StringUtils.hasText(userid)) {
            throw new CustomException(NO_ARGUMENT_USERID.getCode(), NO_ARGUMENT_USERID.getMessage());
        }
        else if(userid.length() <4 || userid.length() >10){
            throw new CustomException(NOT_MATCHED_USERID.getCode(),NOT_MATCHED_USERID.getMessage());
        }
        return repository.existsByUserid(userid);
    }

    // 닉네임 중복 체크
    public Boolean isExistNickname(String nickname) {

        log.info("nickname ={}", nickname);

        if (!StringUtils.hasText(nickname)) {
            log.info("nicknameHasText ={}", nickname);
            throw new CustomException(NO_ARGUMENT_NICKNAME.getCode(), NO_ARGUMENT_NICKNAME.getMessage());
        } else if (nickname.length() < 2 || nickname.length() > 10) {
            log.info("nicknamelong ={}", nickname);
            throw new CustomException(NOT_MATCHED_NICKNAME.getCode(), NOT_MATCHED_NICKNAME.getMessage());
        }
        return repository.existsByNickName(nickname);
    }

    // 핸드폰 번호 중복확인
    public Boolean isExistPhone(String phone) {
        return repository.existsByPhone(phone);
    }

    /**
     * 아이디 / 비밀번호 찾기
     */

    public String findUserInfo(FindUserDto dto) {

        Users findInfo = new Users();
        String result = "";

        if (StringUtils.hasText(dto.getUserid())) {

            findInfo = repository.findByPhoneAndUserid(dto.getPhone(), dto.getUserid())
                    .orElseThrow(()->new NoSuchElementException("존재하지 않는 계정입니다."));
            result = findInfo.getPassword();
        } else{

            findInfo = repository.findByPhoneAndUserName(dto.getPhone(), dto.getUserName())
                    .orElseThrow(()->new NoSuchElementException("존재하지 않는 계정입니다."));
            result = findInfo.getUserid();
        }

        return result;
    }


    // 회원가입
    @Transactional
    public Users save(Users dto) {

        try {
            return repository.save(dto);

        } catch (EntityExistsException | DataIntegrityViolationException e) {
            throw new IllegalArgumentException();
        }
    }

    @Transactional
    public Users update(UpdateUserDto dto) {
        try {
            Users updateUser = repository.findById(dto.getId())
                    .orElseThrow(() -> new NoSuchElementException("잘못된 접근입니다."));
            updateUser.updateUserInfo(dto);
            return repository.save(updateUser);
        } catch (Exception e) {
            throw new CustomException(UPDATE_ERROR.getCode(), UPDATE_ERROR.getMessage());
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new CustomException(DELETE_ERROR.getCode(), DELETE_ERROR.getMessage());
        }
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
