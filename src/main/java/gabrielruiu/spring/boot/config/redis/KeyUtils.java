package gabrielruiu.spring.boot.config.redis;

/**
 * @author Gabriel Mihai Ruiu (gabriel.ruiu@mail.com)
 */
class KeyUtils {

    static String applicationProperties(String application, String profile, String label) {
        return String.format("%s:%s:%s:*", application, profile, label);
    }

    static String extractPropertyNameNameFromKey(String application, String profile, String label, String key) {
        return key.replace(String.format("%s:%s:%s:", application, profile, label), "");
    }
}
