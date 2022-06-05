package com.github.AlexanderSobko.MatteoSweetsBot;

import com.github.AlexanderSobko.MatteoSweetsBot.core.SpringConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class MatteoSweetsBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringConfig.class,args);
    }

}
