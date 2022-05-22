package com.github.AlexanderSobko.MatteoSweetsBot.handlers.callback_handlers;

import com.github.AlexanderSobko.MatteoSweetsBot.handlers.BaseHandler;
import com.github.AlexanderSobko.MatteoSweetsBot.services.BotUserService;
import com.github.AlexanderSobko.MatteoSweetsBot.services.OrderService;
import com.github.AlexanderSobko.MatteoSweetsBot.services.PatisserieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
public class ConnectOperatorHandler extends BaseHandler {

    private static final String CONTACT_TEXT = "@Matteo_sweets";

    @Override
    public List<Object> handle(Update update) {
        String chatId = update.getCallbackQuery().getMessage().getChatId().toString();
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();

        List<Object> messages = new ArrayList<>();
        messages.add(getMessage(chatId, CONTACT_TEXT, messageId));
        return messages;
    }

    @Override
    protected BotApiMethod<? extends Serializable> getMessage(String chatId, String callbackData, Integer messageId) {
        return SendMessage.builder()
                .chatId(chatId)
                .text(callbackData)
                .build();
    }

    @Autowired
    public ConnectOperatorHandler(BotUserService botUserService,
                                  OrderService orderService,
                                  PatisserieService patisserieService) {
        super(botUserService, orderService, patisserieService);
    }
}
