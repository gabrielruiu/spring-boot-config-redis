package gabrielruiu.spring.boot.config.redis;

/**
 * @author Gabriel Mihai Ruiu (gabriel.ruiu@mail.com)
 */
class KeyUtils {

    static String globalPropertyKeys(String profile) {
        return applicationProperties("application", profile);
    }

    static String applicationProperties(String application, String profile) {
        return String.format("%s:%s:*", application, profile);
    }
}
