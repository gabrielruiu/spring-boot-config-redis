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

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public Set<String> getKeys(String application, String profiles, String label) {
        Set<String> allKeys = new HashSet<>();
        allKeys.addAll(findGlobalPropertyKeys(profiles));
        allKeys.addAll(findApplicationKeys(application, profiles));
        return allKeys;
    }

    private Set<String> findGlobalPropertyKeys(String profiles) {
        Set<String> globalPropertyKeys = new HashSet<>();
        for (String profile : StringUtils.commaDelimitedListToSet(profiles)) {
            globalPropertyKeys.addAll(stringRedisTemplate.keys(KeyUtils.globalPropertyKeys(profile)));
        }
        return globalPropertyKeys;
    }

    private Set<String> findApplicationKeys(String application, String profiles) {
        Set<String> applicationKeys = new HashSet<>();
        for (String profile : StringUtils.commaDelimitedListToSet(profiles)) {
            applicationKeys.addAll(stringRedisTemplate.keys(KeyUtils.applicationProperties(application, profile)));
        }
        return applicationKeys;
    }
}
