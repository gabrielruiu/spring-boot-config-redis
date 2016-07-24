package gabrielruiu.spring.boot.config.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.environment.PropertySource;
import org.springframework.cloud.config.server.config.ConfigServerProperties;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author Gabriel Mihai Ruiu (gabriel.ruiu@mail.com)
 */
@Component
public class RedisEnvironmentRepository implements EnvironmentRepository {

    private static final String DEFAULT_LABEL = "master";

    @Autowired
    private RedisConfigPropertySourceProvider redisConfigPropertySourceProvider;

    @Autowired
    private ConfigServerProperties configServerProperties;

    @Override
    public Environment findOne(String application, String profileArray, String label) {
        if (label == null) {
            if (configServerProperties.getDefaultLabel() != null) {
                label = configServerProperties.getDefaultLabel();
            } else {
                label = DEFAULT_LABEL;
            }
        }
        if (profileArray == null) {
            profileArray = configServerProperties.getDefaultProfile();
        } else {
            String[] profiles = profileArray.split(",");
            if (profiles.length > 0) {
                boolean defaultProfileFound = false;
                for (String profile : profiles) {
                    if (profile.equals(configServerProperties.getDefaultProfile())) {
                        defaultProfileFound = true;
                        break;
                    }
                }
                if (!defaultProfileFound) {
                    profileArray = profileArray + "," + configServerProperties.getDefaultProfile();
                }
            }
        }
        if (application == null) {
            application = configServerProperties.getDefaultApplicationName();
        }

        String[] profiles = profileArray.split(",");
        Environment environment = new Environment(application, profiles);

        for (String profile : profiles) {

            String globalApplicationName = configServerProperties.getDefaultApplicationName();
            PropertySource globalPropertySource = redisConfigPropertySourceProvider.getPropertySource(globalApplicationName, profile, label);
            if (globalPropertySource != null) {
                environment.add(globalPropertySource);
            }

            if (!Objects.equals(application, configServerProperties.getDefaultApplicationName())) {
                PropertySource applicationPropertySource = redisConfigPropertySourceProvider.getPropertySource(application, profile, label);
                if (applicationPropertySource != null) {
                    environment.add(applicationPropertySource);
                }
            }
        }

        return environment;
    }
}
