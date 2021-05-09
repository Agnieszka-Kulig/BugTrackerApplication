package bug.com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;


@SpringBootApplication
@EnableGlobalMethodSecurity(securedEnabled = true)
public class BugTracker2Application {

    public static void main(String[] args) {
        SpringApplication.run(BugTracker2Application.class, args);
    }

}
