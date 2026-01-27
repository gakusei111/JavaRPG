package com.rpg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RpgApiApplication {
    public static void main(String[] args) {
        // これだけでTomcat（Webサーバー）が起動します！
        SpringApplication.run(RpgApiApplication.class, args);
    }
}