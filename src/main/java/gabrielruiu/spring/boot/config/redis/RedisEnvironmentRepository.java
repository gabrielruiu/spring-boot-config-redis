package gabrielruiu.spring.boot.config.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author Gabriel Mihai Ruiu (gabriel.ruiu@mail.com)
 */
public class RedisEnvironmentRepository implements EnvironmentRepository {

    private static final String KEY = "config";

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public Environment findOne(String application, String profile, String label) {
        return null;
    }
}
