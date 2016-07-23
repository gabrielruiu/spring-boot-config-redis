package gabrielruiu.spring.boot.config.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.config.environment.PropertySource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Gabriel Mihai Ruiu (gabriel.ruiu@mail.com)
 */
@Component
public class RedisConfigPropertySourceProvider {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisConfigKeysProvider redisConfigKeysProvider;

    public PropertySource getPropertySource(String application, String profile, String label) {
        List<String> keys = new ArrayList<>(redisConfigKeysProvider.getKeys(application, profile, label));
        if (keys.size() > 0) {
            List<String> propertyValues = stringRedisTemplate.opsForValue().multiGet(keys);
            return buildPropertySource(application, keys, propertyValues);
        }
        return null;
    }

    private PropertySource buildPropertySource(String application, List<String> keys, List<String> propertyValues) {
        Map<String, String> properties = new HashMap<>();
        for (int i=0; i<keys.size(); i++) {
            properties.put(keys.get(i), propertyValues.get(i));
        }
        return new PropertySource(application, properties);
    }
}
