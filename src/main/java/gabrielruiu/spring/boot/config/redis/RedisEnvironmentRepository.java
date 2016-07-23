package gabrielruiu.spring.boot.config.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.environment.PropertySource;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author Gabriel Mihai Ruiu (gabriel.ruiu@mail.com)
 */
@Component
public class RedisEnvironmentRepository implements EnvironmentRepository {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RedisConfigKeysProvider redisConfigKeysProvider;

    @Override
    public Environment findOne(String application, String profile, String label) {
        Environment environment = new Environment(application, profile);
        Set<String> globalPropertyKeys = redisConfigKeysProvider.getKeys(KeyUtils.GLOBAL_APPLICATION_KEY, profile, label);
        Set<String> applicationPropertyKeys = new HashSet<>();
        if (!application.equals(KeyUtils.GLOBAL_APPLICATION_KEY)) {
            applicationPropertyKeys = redisConfigKeysProvider.getKeys(application, profile, label);
        }

        if (globalPropertyKeys.size() > 0) {
            List<String> globalPropertyValues = redisTemplate.opsForValue().multiGet(globalPropertyKeys);
            PropertySource globalPropertySource = buildPropertySource(KeyUtils.GLOBAL_APPLICATION_KEY, new ArrayList<>(globalPropertyKeys), globalPropertyValues);
            environment.add(globalPropertySource);
        }

        if (applicationPropertyKeys.size() > 0) {
            List<String> applicationPropertyValues = redisTemplate.opsForValue().multiGet(applicationPropertyKeys);
            PropertySource applicationPropertySource = buildPropertySource(application, new ArrayList<>(applicationPropertyKeys), applicationPropertyValues);
            environment.add(applicationPropertySource);
        }

        return environment;
    }

    private PropertySource buildPropertySource(String application, List<String> keys, List<String> propertyValues) {
        Map<String, String> properties = new HashMap<>();
        for (int i=0; i<keys.size(); i++) {
            properties.put(keys.get(i), propertyValues.get(i));
        }
        return new PropertySource(application, properties);
    }
}
