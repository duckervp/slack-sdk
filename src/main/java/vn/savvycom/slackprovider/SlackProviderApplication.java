package vn.savvycom.slackprovider;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@ServletComponentScan
public class SlackProviderApplication {
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
    public static void main(String[] args) {
        SpringApplication.run(SlackProviderApplication.class, args);
    }

}
