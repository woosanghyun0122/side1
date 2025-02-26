package side.shopping.repository.admin.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * side.shopping.repository.admin.dto.QUserListDto is a Querydsl Projection type for UserListDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QUserListDto extends ConstructorExpression<UserListDto> {

    private static final long serialVersionUID = 1206346771L;

    public QUserListDto(com.querydsl.core.types.Expression<String> userid, com.querydsl.core.types.Expression<String> userName, com.querydsl.core.types.Expression<String> nickName, com.querydsl.core.types.Expression<String> phone, com.querydsl.core.types.Expression<String> email, com.querydsl.core.types.Expression<side.shopping.domain.users.Role> role) {
        super(UserListDto.class, new Class<?>[]{String.class, String.class, String.class, String.class, String.class, side.shopping.domain.users.Role.class}, userid, userName, nickName, phone, email, role);
    }

}

