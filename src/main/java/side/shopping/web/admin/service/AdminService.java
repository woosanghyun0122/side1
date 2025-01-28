package side.shopping.web.admin.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import side.shopping.exception.CustomException;
import side.shopping.exception.ErrorCode;
import side.shopping.repository.admin.AdminRepositoryImpl;
import side.shopping.repository.admin.dto.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static side.shopping.exception.ErrorCode.VARIABLE_ERROR;


@Service
@Slf4j
public class AdminService {

    @Autowired
    private AdminRepositoryImpl repository;

    public Page<UserListDto> findUser(Pageable pageable, AdminConditionDto dto) {

        if (dto == null) {
            throw new CustomException(VARIABLE_ERROR.getCode(), VARIABLE_ERROR.getMessage());
        }

        return repository.findAllUser(pageable, dto);
    }

    public Page<ProductListDto> findProduct(Pageable pageable, AdminConditionDto dto) {

        if (dto == null) {
            throw new CustomException(VARIABLE_ERROR.getCode(), VARIABLE_ERROR.getMessage());
        }

        return repository.findAllProduct(pageable, dto);
    }

    public Page<OrderListDto> findOrder(Pageable pageable, AdminConditionDto dto) {

        if (dto == null) {
            throw new CustomException(VARIABLE_ERROR.getCode(), VARIABLE_ERROR.getMessage());
        }

        return repository.findAllOrder(pageable, dto);
    }

    public Page<PaymentListDto> findAllPayment(Pageable pageable, AdminConditionDto dto) {

        if (dto == null) {
            throw new CustomException(VARIABLE_ERROR.getCode(), VARIABLE_ERROR.getMessage());
        }

        return repository.findAllPayment(pageable, dto);
    }



}
