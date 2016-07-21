package gabrielruiu.spring.boot.config.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author Gabriel Mihai Ruiu (gabriel.ruiu@mail.com)
 */
@Component
public class RedisEnvironmentRepository implements EnvironmentRepository {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public Environment findOne(String application, String profile, String label) {
        return dummyEnvironment(application, profile, label);
    }

    private Set<String> findGlobalPropertyKeys(String profiles) {
        Set<String> globalPropertyKeys = new HashSet<>();
        for (String profile : StringUtils.commaDelimitedListToSet(profiles)) {
            globalPropertyKeys.addAll(redisTemplate.keys(KeyUtils.globalPropertyKeys(profile)));
        }
        return globalPropertyKeys;
    }

    private Set<String> findApplicationKeys(String application, String profiles) {
        Set<String> applicationKeys = new HashSet<>();
        for (String profile : StringUtils.commaDelimitedListToSet(profiles)) {
            applicationKeys.addAll(redisTemplate.keys(KeyUtils.applicationProperties(application, profile)));
        }
        return applicationKeys;
    }

    private Environment dummyEnvironment(String application, String profile, String label) {
        return new Environment(application, StringUtils.commaDelimitedListToStringArray(profile),label, UUID.randomUUID().toString());
    }
}
