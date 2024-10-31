package side.shopping.repository.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import side.shopping.domain.product.Category;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

    // 카테고리 뎁스별 조회
    List<Category> findByDepth(int depth);

    // 하위 뎁스 조회
    List<Category> findByDepthAndParentId(int depth, String parentId);

}
