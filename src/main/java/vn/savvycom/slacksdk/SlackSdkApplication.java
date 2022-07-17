package vn.savvycom.slacksdk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class SlackSdkApplication {

    public static void main(String[] args) {
        SpringApplication.run(SlackSdkApplication.class, args);
    }

}
