package side.shopping.repository.admin.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import side.shopping.domain.users.Role;

@Getter
@Builder
@NoArgsConstructor
public class UserListDto {

    /**
     * 관리자 회원 조회 시 사용하는 객체
     * */

    String userid;
    String userName;
    String nickName;
    String phone;
    String email;
    Role role;

    @QueryProjection
    public UserListDto(String userid, String userName, String nickName, String phone, String email, Role role) {
        this.userid = userid;
        this.userName = userName;
        this.nickName = nickName;
        this.phone = phone;
        this.email = email;
        this.role = role;
    }
}
