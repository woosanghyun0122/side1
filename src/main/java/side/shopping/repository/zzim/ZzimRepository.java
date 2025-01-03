package side.shopping.repository.zzim;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import side.shopping.domain.zzim.Zzim;

import java.util.List;

@Repository
public interface ZzimRepository extends JpaRepository<Zzim,Long> {

    List<Zzim> findByUser_useridOrderByCreatedAtDesc(String userid);
}
