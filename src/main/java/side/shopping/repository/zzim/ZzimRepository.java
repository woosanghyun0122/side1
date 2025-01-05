package side.shopping.repository.zzim;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import side.shopping.domain.zzim.Zzim;

import java.util.List;
import java.util.Optional;

@Repository
public interface ZzimRepository extends JpaRepository<Zzim,Long> {

    //찜 리스트 조회
    List<Zzim> findByUser_useridOrderByCreatedAtDesc(String userid);

    //찜 삭제를 위한 조회
    Optional<Zzim> findByUser_useridAndProduct_productId(String userid, Long productId);
}
