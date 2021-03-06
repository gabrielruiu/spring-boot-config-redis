package gabrielruiu.spring.boot.config.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Gabriel Mihai Ruiu (gabriel.ruiu@mail.com)
 */
@Component
public class RedisConfigKeysProvider {

    private StringRedisTemplate stringRedisTemplate;
    private RedisConfigKeysUtilities redisConfigKeysUtilities;

    @Autowired
    public RedisConfigKeysProvider(StringRedisTemplate stringRedisTemplate, RedisConfigKeysUtilities redisConfigKeysUtilities) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.redisConfigKeysUtilities = redisConfigKeysUtilities;
    }

    public Set<String> getKeys(String application, String profiles, String label) {
        Set<String> applicationKeys = new HashSet<>();
        for (String profile : StringUtils.commaDelimitedListToSet(profiles)) {
            applicationKeys.addAll(stringRedisTemplate.keys(redisConfigKeysUtilities.redisConfigKeyTemplate(application, profile, label)));
        }
        return applicationKeys;
    }
}
