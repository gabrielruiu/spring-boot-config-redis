package gabrielruiu.spring.boot.config.redis;

import org.springframework.stereotype.Component;

/**
 * @author Gabriel Mihai Ruiu (gabriel.ruiu@mail.com)
 */
@Component
public class RedisConfigKeysUtilities {

    private static final String KEY_FORMAT = "%s:%s:%s:";

    public String redisConfigKeyTemplate(String application, String profile, String label) {
        return String.format(KEY_FORMAT + "*", application, profile, label);
    }

    public String extractPropertyNameNameFromKey(String application, String profile, String label, String key) {
        return key.replace(String.format(KEY_FORMAT, application, profile, label), "");
    }
}
