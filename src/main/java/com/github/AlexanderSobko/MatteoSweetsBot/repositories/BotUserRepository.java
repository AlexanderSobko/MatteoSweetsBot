package com.github.AlexanderSobko.MatteoSweetsBot.repositories;

import com.github.AlexanderSobko.MatteoSweetsBot.entities.BotUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;

@Repository
public interface BotUserRepository extends JpaRepository<BotUser, Long> {

    boolean existsByTelegramId(String telegramId);

    BotUser findByTelegramId(String telegramId);
}
