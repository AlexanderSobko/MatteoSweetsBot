package com.github.AlexanderSobko.MatteoSweetsBot.services;

import com.github.AlexanderSobko.MatteoSweetsBot.entities.BotUser;
import com.github.AlexanderSobko.MatteoSweetsBot.models.UserStatus;
import com.github.AlexanderSobko.MatteoSweetsBot.repositories.BotUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.HashMap;
import java.util.Map;

@Service
public class BotUserService {

    private final BotUserRepository userRepo;
    private final Map<String, UserStatus> usersStatus;

    public void save(BotUser botUser){
        userRepo.save(botUser);
    }

    public boolean isPresent(BotUser botUser) {
        return userRepo.existsByTelegramId(botUser.getTelegramId());
    }

    public BotUser userToBotUser(User user) {
        BotUser botUser = new BotUser();
        botUser.setTelegramId(user.getId() + "");
        botUser.setFirstName(user.getFirstName());
        botUser.setUserName(user.getUserName());
        botUser.setLastName(user.getLastName());
        return botUser;
    }

    public BotUser getUserById(String telegramId) {
        return userRepo.findByTelegramId(telegramId);
    }

    public UserStatus getUserStatus(String userId) {
        return usersStatus.get(userId);
    }

    public void setUserStatus(String userId, UserStatus userStatus) {
        usersStatus.put(userId, userStatus);
    }

    @Autowired
    public BotUserService(BotUserRepository userRepo) {
        this.userRepo = userRepo;
        this.usersStatus = new HashMap<>();
    }

}
