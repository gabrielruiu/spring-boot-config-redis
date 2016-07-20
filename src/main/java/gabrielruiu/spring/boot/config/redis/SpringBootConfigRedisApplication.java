package gabrielruiu.spring.boot.config.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class SpringBootConfigRedisApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootConfigRedisApplication.class, args);
	}
}
