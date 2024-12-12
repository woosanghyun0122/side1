package side.shopping.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;


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

    public void removeCache(String key) {

        log.info("redis deleteKey={}", key);
        template.delete(key);
    }

    public String createKey() {

        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("YYYY-MM-dd_HH:mm:ss");
        String key = UUID.randomUUID()
                .toString().replace("-", "").substring(0, 8) + "_"
                + time.format(dateTimeFormatter);
        return key;
    }
}
