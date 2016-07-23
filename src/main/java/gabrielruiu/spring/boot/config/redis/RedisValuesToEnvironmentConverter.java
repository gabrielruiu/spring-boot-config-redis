package gabrielruiu.spring.boot.config.redis;

import org.springframework.cloud.config.environment.Environment;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Gabriel Mihai Ruiu (gabriel.ruiu@mail.com)
 */
public class RedisValuesToEnvironmentConverter {

    public Environment convert(String application, String profiles, String label, Map<String, Set<String>> values) {
        Environment environment = new Environment(application, StringUtils.commaDelimitedListToStringArray(profiles));
        environment.setLabel(label);
        environment.setVersion("unset");
        convert(environment, new HashMap<>());
        return environment;
    }

    private void convert(Environment environment, Map<String, Set<String>> values) {
        for (Map.Entry<String, Set<String>> propertyValueMap : values.entrySet()) {
            Map<String, String> applicationProperties = new HashMap<>();
            for (String propertyValue : propertyValueMap.getValue()) {
//                applicationProperties.put(String.format("%s.%s", propertyValueMap.getKey(), propertyValue));
            }
        }
    }
}
