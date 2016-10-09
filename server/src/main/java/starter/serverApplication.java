package starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by nians on 2016/10/7.
 */

@SpringBootApplication
@Configuration
@ComponentScan
@EnableAutoConfiguration
public class serverApplication {
    public static void main(String[] args) {
        SpringApplication.run(serverApplication.class, args);
    }
}
