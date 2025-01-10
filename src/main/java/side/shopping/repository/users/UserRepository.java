package side.shopping.repository.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import side.shopping.domain.users.Users;
import side.shopping.repository.admin.AdminRepository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users,Long>, AdminRepository {

    // 로그인
    Optional<Users> findByUserid(String userid);

    // 회원가입 시 아이디 중복 여부
    Boolean existsByUserid(String userid);

    // 회원가입 시 닉네임 중복 여부
    Boolean existsByNickName(String nickName);

    // 회원가입 시 전화번호 중복확인
    Boolean existsByPhone(String phone);

    //아이디 찾기
    Optional<Users> findByPhoneAndUserName(String phone,String userName);

    //비밀번호 찾기
    Optional<Users> findByPhoneAndUserid(String phone,String userid);

    // 회원 가입
    Users save(Users user);

    // 회원 탈퇴
    void deleteById(Long id);


}
