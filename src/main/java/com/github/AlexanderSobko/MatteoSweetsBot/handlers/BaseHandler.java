package com.github.AlexanderSobko.MatteoSweetsBot.handlers;

import com.github.AlexanderSobko.MatteoSweetsBot.services.BotUserService;
import com.github.AlexanderSobko.MatteoSweetsBot.services.OrderService;
import com.github.AlexanderSobko.MatteoSweetsBot.services.PatisserieService;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import java.io.Serializable;


public abstract class BaseHandler implements Handler {

    protected final BotUserService botUserService;
    protected final OrderService orderService;
    protected final PatisserieService patisserieService;

    protected abstract BotApiMethod<? extends Serializable> getMessage(String chatId, String callbackData, Integer messageId);

    public BaseHandler(BotUserService botUserService, OrderService orderService, PatisserieService patisserieService) {
        this.botUserService = botUserService;
        this.orderService = orderService;
        this.patisserieService = patisserieService;
    }
}
