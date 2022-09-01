package com.github.AlexanderSobko.MatteoSweetsBot.handlers.message_handlers;

import com.github.AlexanderSobko.MatteoSweetsBot.models.entities.Order;
import com.github.AlexanderSobko.MatteoSweetsBot.handlers.BaseHandler;
import com.github.AlexanderSobko.MatteoSweetsBot.services.UserService;
import com.github.AlexanderSobko.MatteoSweetsBot.services.PatisserieService;
import com.github.AlexanderSobko.MatteoSweetsBot.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderHistoryButtonHandler extends BaseHandler {

    @Override
    public List<Object> handle(Update update) {
        String userId = update.getMessage().getChatId().toString();
        List<Order> orders = orderService.findAllByUserId(userId);
        List<Object> messages = new ArrayList<>();

        if (orders.isEmpty())
            messages.add(getMessage( userId, "Ваша история заказов пуста", null));
        else
            messages = orders.stream()
                    .map(o -> this.getMessage(userId, orderService.getOrderInfo(o), null))
                    .collect(Collectors.toList());

        return messages;
    }

    @Override
    protected BotApiMethod<? extends Serializable> getMessage(String chatId, String callbackData, Integer messageId) {
        return SendMessage.builder()
                .text(callbackData)
                .chatId(chatId)
                .replyToMessageId(messageId)
                .build();
    }

    @Autowired
    public OrderHistoryButtonHandler(UserService UserService,
                                     OrderService orderService,
                                     PatisserieService patisserieService) {
        super(UserService, orderService, patisserieService);
    }
}
