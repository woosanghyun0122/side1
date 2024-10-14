package side.shopping.repository.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import side.shopping.domain.users.Users;
import side.shopping.repository.users.dto.users.LoginResponseDto;
import side.shopping.repository.users.dto.users.PersistUserDto;
import side.shopping.repository.users.dto.users.UpdateUserDto;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users,Long> {

    // 로그인
    Optional<Users> findByUserid(String userid);

    // 회원가입 시 아이디 중복 여부
    Boolean existsByUserid(String userid);

    // 회원가입 시 닉네임 중복 여부
    Boolean existsByNickName(String nickName);

    // 회원 가입
    Users save(Users user);

    // 회원 탈퇴
    void deleteById(Long id);


}
