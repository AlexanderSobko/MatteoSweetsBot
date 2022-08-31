package com.github.AlexanderSobko.MatteoSweetsBot.handlers;

import com.github.AlexanderSobko.MatteoSweetsBot.services.UserService;
import com.github.AlexanderSobko.MatteoSweetsBot.services.OrderService;
import com.github.AlexanderSobko.MatteoSweetsBot.services.PatisserieService;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import java.io.Serializable;


public abstract class BaseHandler implements Handler {

    protected final UserService UserService;
    protected final OrderService orderService;
    protected final PatisserieService patisserieService;

    protected abstract BotApiMethod<? extends Serializable> getMessage(String chatId, String callbackData, Integer messageId);

    public BaseHandler(UserService UserService, OrderService orderService, PatisserieService patisserieService) {
        this.UserService = UserService;
        this.orderService = orderService;
        this.patisserieService = patisserieService;
    }
}
