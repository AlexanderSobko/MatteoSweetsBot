package com.github.AlexanderSobko.MatteoSweetsBot.repositories;

import com.github.AlexanderSobko.MatteoSweetsBot.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MSUserRepository extends JpaRepository<User, Long> {

    boolean existsByTelegramId(String telegramId);

    User findByTelegramId(String telegramId);
}
