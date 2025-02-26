/*
package side.shopping.web.admin.service;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import side.shopping.repository.admin.dto.AdminConditionDto;
import side.shopping.repository.admin.dto.ProductListDto;
import side.shopping.repository.admin.dto.UserListDto;
import side.shopping.web.admin.TestConfig;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@Import(TestConfig.class)
class AdminServiceTest {

    @Autowired
    AdminService service;

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
    void findUser() {

        Pageable pageable = PageRequest.of(0, 5);
        AdminConditionDto dto = setDto();
        dto.setUserid("");
        Page<UserListDto> result = service.findUser(pageable, dto);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).isNotEmpty();
        assertThat(result.getTotalElements()).isGreaterThan(0);
        log.info("result={}", result.getTotalPages());
        log.info("result={}", result.get().count());
    }

    @Test
    void findProduct() {

        Pageable pageable = PageRequest.of(0, 100);
        AdminConditionDto dto = setDto();
        dto.setUserid("seller1");
        Page<ProductListDto> result = service.findProduct(pageable, dto);

        assertThat(result).isNotNull();
        log.info("result={}", result.getTotalElements());
    }

    @Test
    void findOrder() {

        Pageable pageable = PageRequest.of(0, 100);
        AdminConditionDto dto = setDto();
        dto.setUserid("seller1");
        Page<ProductListDto> result = service.findProduct(pageable, dto);

        assertThat(result).isNotNull();
        log.info("result={}", result.getTotalElements());
    }

    @Test
    void findAllPayment() {
    }

    private AdminConditionDto setDto() {

        return AdminConditionDto.builder()
                .userid("normal2")
                .startDate(LocalDateTime.of(2024,1,1,00,00,00).toString())
                .endDate(LocalDateTime.of(2025,2,1,00,00,00).toString())
                .build();
    }

}*/
