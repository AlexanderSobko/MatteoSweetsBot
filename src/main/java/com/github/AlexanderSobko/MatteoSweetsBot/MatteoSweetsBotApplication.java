package com.github.AlexanderSobko.MatteoSweetsBot;

import com.github.AlexanderSobko.MatteoSweetsBot.core.SpringConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@SpringBootApplication
public class MatteoSweetsBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringConfig.class,args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry
                        .addMapping("/**")
                        .allowedMethods("*")
                        .allowedOrigins("*")
                        .maxAge(3600);
            }
        };
    }

}
