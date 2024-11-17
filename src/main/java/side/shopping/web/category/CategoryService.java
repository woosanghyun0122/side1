package side.shopping.web.category;

import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import side.shopping.domain.product.Category;
import side.shopping.exception.CustomException;
import side.shopping.exception.ErrorCode;
import side.shopping.repository.category.CategoryRepository;

import java.util.List;
import java.util.NoSuchElementException;

import static side.shopping.exception.ErrorCode.*;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;


    /**
     *  뎁스별 카테고리 조회 조회
     * */
    public List<Category> findDepth(int i) {


        List<Category> list = repository.findByDepth(i);

        if (list == null) {
            throw new CustomException(SERVER_ERROR.getCode(), SERVER_ERROR.getMessage());
        }

        return list;
    }

    /**
     * 하위 뎁스 조회
     */
    public List<Category> findLowerDepth(int depth, String parentId) {

        List<Category> list = repository.findByDepthAndParentId(depth, parentId);

        if (list == null) {
            throw new CustomException(SERVER_ERROR.getCode(), SERVER_ERROR.getMessage());
        }

        return list;
    }

    /**
     * 저장
     * */
    public Category save(Category category) {

        try {
            return repository.save(category);
        } catch (EntityExistsException | DataIntegrityViolationException e) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * 수정
     */
    public void modify(Category category) {

        try {
            Category updateCategory = repository.findById(category.getCategoryId())
                    .orElseThrow(() -> new CustomException(SERVER_ERROR.getCode(), SERVER_ERROR.getMessage()));

            updateCategory.update(category);
            repository.save(updateCategory);
        } catch (Exception e) {
            throw new CustomException(UPDATE_ERROR.getCode(), UPDATE_ERROR.getMessage());
        }
    }

    /**
     * 삭제
     */
    public void delete(String categoryId) {

        try {
            repository.deleteById(categoryId);
        } catch (Exception e) {
            throw new CustomException(DELETE_ERROR.getCode(), DELETE_ERROR.getMessage());
        }
    }

}
