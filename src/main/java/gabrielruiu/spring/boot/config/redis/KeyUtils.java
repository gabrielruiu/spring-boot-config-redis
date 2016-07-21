package gabrielruiu.spring.boot.config.redis;

/**
 * @author Gabriel Mihai Ruiu (gabriel.ruiu@mail.com)
 */
class KeyUtils {

    public static final String GLOBAL_APPLICATION_KEY = "application";

    static String globalPropertyKeys(String profile) {
        return applicationProperties(GLOBAL_APPLICATION_KEY, profile);
    }

    static String applicationProperties(String application, String profile) {
        return String.format("%s:%s:*", application, profile);
    }
}
