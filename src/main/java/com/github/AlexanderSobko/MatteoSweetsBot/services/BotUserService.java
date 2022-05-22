package com.github.AlexanderSobko.MatteoSweetsBot.services;

import com.github.AlexanderSobko.MatteoSweetsBot.entities.BotUser;
import com.github.AlexanderSobko.MatteoSweetsBot.repositories.BotUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;

@Service
public class BotUserService {

    private final BotUserRepository userRepo;

    public void save(BotUser botUser){
        userRepo.save(botUser);
    }

    public boolean isPresent(BotUser botUser) {
        return userRepo.existsById(botUser.getId());
    }

    public BotUser userToBotUser(User user) {
        BotUser botUser = new BotUser();
        botUser.setId(user.getId() + "");
        botUser.setFirstName(user.getFirstName());
        botUser.setUserName(user.getUserName());
        botUser.setLastName(user.getLastName());
        return botUser;
    }
    public BotUser getUserById(String userId) {
        return userRepo.findById(userId).get();
    }

    @Autowired
    public BotUserService(BotUserRepository userRepo) {
        this.userRepo = userRepo;
    }

}
