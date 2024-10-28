package side.shopping.web.users.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.MethodArgumentNotValidException;
import side.shopping.domain.users.Role;
import side.shopping.domain.users.Users;
import side.shopping.repository.users.UserRepository;
import side.shopping.repository.users.dto.users.LoginDto;
import side.shopping.repository.users.dto.users.LoginResponseDto;
import side.shopping.repository.users.dto.users.UpdateUserDto;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
class UsersServiceTest {

    @Autowired
    private UsersService service;

    @Autowired
    private UserRepository repository;

    @Autowired
    PlatformTransactionManager transactionManager;
    TransactionStatus status;


    @BeforeEach
    void beforeEach() {
        // 트랜잭션 시작
        status = transactionManager.getTransaction(new DefaultTransactionDefinition());
    }

    @AfterEach
    void afterEach() {
        transactionManager.rollback(status);
    }

    @Test
    void login() throws IllegalAccessException {

        LoginDto loginCheck = new LoginDto("admin", "1234");
        LoginDto loginCheck2 = new LoginDto("admin1", "1234");

        LoginResponseDto user = service.login(loginCheck);
        log.info("user={}", user);

        // 로그인 정상 테스트
        assertThat(user.getUserName()).isEqualTo("김철수");

        // 로그인 예외 테스트
        assertThatThrownBy(() ->
                service.login(loginCheck2)).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void save() throws MethodArgumentNotValidException {

        Users user1 = new Users("test1", "1234", "테스트", "테스트", "01012127878","","서울시 영등포구","", Role.NORMAL);
        Users saveUser = service.save(user1);

        assertThat(service.isExistUserid("test1")).isTrue();
    }

    @Test
    void update() {

        Users user1 = new Users("test1", "1234", "테스트", "테스트", "01012127878","","서울시 영등포구","", Role.NORMAL);
        Users saveUser = service.save(user1);

        log.info("saveUser.getNickName()={}", saveUser.getNickName());

        //update
        UpdateUserDto update = UpdateUserDto.builder()
                .id(saveUser.getId())
                .nickName("맹구")
                .phone(saveUser.getPhone())
                .email(saveUser.getEmail())
                .address1(saveUser.getAddress1()).build();

        service.update(update);
        Users updateInfo = repository.findById(update.getId())
                .orElseThrow(() -> new NoSuchElementException());

        log.info("updateInfo.getNickName()={}", updateInfo.getNickName());

        assertThat(updateInfo.getNickName()).isEqualTo("맹구");
    }

    @Test
    void delete() {

        Users user1 = new Users("test1", "1234", "테스트", "테스트", "01012127878","","서울시 영등포구","", Role.NORMAL);
        Users saveUser = service.save(user1);
       // DELETE
        service.delete(saveUser.getId());
        boolean result = repository.existsById(saveUser.getId());
        assertThat(result).isFalse();
    }




}