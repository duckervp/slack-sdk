package vn.savvycom.slackprovider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class SlackProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(SlackProviderApplication.class, args);
    }

}
