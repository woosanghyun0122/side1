package side.shopping.cache;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.UUID;

@Component("customCacheKeyGenerator")
public class CacheKeyGenerator implements KeyGenerator {

    private String lastGeneratedKey;


    @Override
    public Object generate(Object target, Method method, Object... params) {

        String uuid = UUID.randomUUID().toString()
                .replace("-", "").substring(0, 8); // 전체 UUID 생성
        this.lastGeneratedKey = uuid;

        return uuid;


    }

    public String getLastGeneratedKey() {
        return this.lastGeneratedKey;
    }


}
