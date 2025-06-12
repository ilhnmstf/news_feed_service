package news_feed_service;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class NewsFeedServiceApp {
    public static void main(String[] args) {
        new SpringApplicationBuilder(NewsFeedServiceApp.class)
                .bannerMode(Banner.Mode.OFF)
                .run(args);
    }
}