package com.github.AlexanderSobko.MatteoSweetsBot.core;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
@ComponentScan("com.github.AlexanderSobko.MatteoSweetsBot")
public class SpringConfig {

    @Value("${DB_URL}")
    String dbURL;
    @Value("${DB_USERNAME}")
    String dbUserName;
    @Value("${DB_PASSWORD}")
    String dbPassword;

    @Bean
    public TelegramBot telegramBot(){
        return new TelegramBot();
    }

    @Bean
    public void botsApi(){
        try {
            new TelegramBotsApi(DefaultBotSession.class).registerBot(telegramBot());
        } catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

    @Bean
    public DriverManagerDataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(dbURL);
        dataSource.setPassword(dbPassword);
        dataSource.setUsername(dbUserName);
        return dataSource;
    }

}
