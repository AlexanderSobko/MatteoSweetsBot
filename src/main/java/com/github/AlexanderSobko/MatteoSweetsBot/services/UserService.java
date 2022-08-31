package com.github.AlexanderSobko.MatteoSweetsBot.services;

import com.github.AlexanderSobko.MatteoSweetsBot.entities.User;
import com.github.AlexanderSobko.MatteoSweetsBot.models.UserStatus;
import com.github.AlexanderSobko.MatteoSweetsBot.repositories.MSUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    private final MSUserRepository userRepo;
    private final Map<String, UserStatus> usersStatus;

    public User save(User User){
        return userRepo.save(User);
    }

    public boolean isPresent(User User) {
        return userRepo.existsByTelegramId(User.getTelegramId());
    }

    public User userToBotUser(org.telegram.telegrambots.meta.api.objects.User user) {
        User botUser = new User();
        botUser.setTelegramId(user.getId() + "");
        botUser.setFirstName(user.getFirstName());
        botUser.setUsername(user.getUserName());
        botUser.setLastName(user.getLastName());
        return botUser;
    }

    public User getUserByTelegramId(String telegramId) {
        return userRepo.findByTelegramId(telegramId);
    }

    public UserStatus getUserStatus(String userId) {
        return usersStatus.get(userId);
    }

    public void setUserStatus(String userId, UserStatus userStatus) {
        usersStatus.put(userId, userStatus);
    }

    public List<User> getMSUsers() {
        return userRepo.findAll();
    }

    public void deleteUser(User user) {
        userRepo.delete(user);
    }

    @Autowired
    public UserService(MSUserRepository userRepo) {
        this.userRepo = userRepo;
        this.usersStatus = new HashMap<>();
    }

    public Optional<User> getUserById(Long id) {
        return userRepo.findById(id);
    }
}
