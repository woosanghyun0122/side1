package side.shopping.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class CacheService {

    private final RedisTemplate<String, Object> template;


    public CacheService(RedisTemplate<String, Object> template) {
        this.template = template;
    }

    public void setOrderList(String key, Object list) {

        log.info("redis saveKey={}", key);
        template.opsForValue().set(key,list);
    }



    public Object getCacheValue(String key) {

        log.info("redis selectKey={}", key);
        return template.opsForValue().get(key);

    }
}
