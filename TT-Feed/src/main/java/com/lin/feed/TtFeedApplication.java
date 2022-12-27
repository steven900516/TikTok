package com.lin.feed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TtFeedApplication {

    public static void main(String[] args) {
        SpringApplication.run(TtFeedApplication.class, args);
    }

}
