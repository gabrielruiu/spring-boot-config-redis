package gabrielruiu.spring.boot.config.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.environment.PropertySource;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;
import org.springframework.stereotype.Component;

/**
 * @author Gabriel Mihai Ruiu (gabriel.ruiu@mail.com)
 */
@Component
public class RedisEnvironmentRepository implements EnvironmentRepository {

    @Autowired
    private RedisConfigPropertySourceProvider redisConfigPropertySourceProvider;

    @Override
    public Environment findOne(String application, String profileArray, String label) {
        String[] profiles = profileArray.split(",");
        Environment environment = new Environment(application, profiles);
        for (String profile : profiles) {

            PropertySource globalPropertySource = redisConfigPropertySourceProvider.getPropertySource(KeyUtils.GLOBAL_APPLICATION_KEY, profile, label);
            if (globalPropertySource != null) {
                environment.add(globalPropertySource);
            }

            PropertySource applicationPropertySource = redisConfigPropertySourceProvider.getPropertySource(application, profile, label);
            if (applicationPropertySource != null) {
                environment.add(applicationPropertySource);
            }
        }
        return environment;
    }


}
