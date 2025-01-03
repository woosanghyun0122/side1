package side.shopping.web.zzim.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import side.shopping.domain.product.Product;
import side.shopping.domain.users.Users;
import side.shopping.domain.zzim.Zzim;
import side.shopping.exception.CustomException;
import side.shopping.repository.product.ProductRepository;
import side.shopping.repository.users.UserRepository;
import side.shopping.repository.zzim.ZzimRepository;

import java.util.List;

import static side.shopping.exception.ErrorCode.*;

@Slf4j
@Service
public class ZzimService {

    @Autowired
    private ZzimRepository repository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * 찜 목록 조회
     */
    public List<Zzim> findZzimList(String userid) {
        return repository.findByUser_useridOrderByCreatedAtDesc(userid);
    }

    /**
     * 찜 등록
     */
    @Transactional
    public Zzim saveZzim(long productId, String userid) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(SELECT_ERROR.getCode(), SELECT_ERROR.getMessage()));

        Users user = userRepository.findByUserid(userid)
                .orElseThrow(() -> new CustomException(SELECT_ERROR.getCode(), SERVER_ERROR.getMessage()));

        Zzim zzim = Zzim.builder()
                .product(product)
                .user(user)
                .build();

        return repository.save(zzim);
    }

    /**
     * 찜 삭제
     */
    @Transactional
    public void delete(Long id) {

        try {
            repository.deleteById(id);
        } catch (Exception e) {
            log.info("error={}", e);
            throw new CustomException(DELETE_ERROR.getCode(), DELETE_ERROR.getMessage());
        }

    }
}
