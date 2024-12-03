package side.shopping.cache;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;
import side.shopping.repository.product.dto.FindProductDto;

import java.util.List;

@Service
public class CacheService {

    private final RedisTemplate<String, List<FindProductDto>> template;

    public CacheService(RedisTemplate<String, List<FindProductDto>> template) {
        this.template = template;
    }

    public void setOrderList(String key ,List<FindProductDto> list) {

        template.opsForValue().set(key,list);
    }

    public List<FindProductDto> getCacheValue(String key) {

        return template.opsForValue().get(key);

    }
}
