package com.github.AlexanderSobko.MatteoSweetsBot.handlers.callback_handlers;

import com.github.AlexanderSobko.MatteoSweetsBot.handlers.BaseHandler;
import com.github.AlexanderSobko.MatteoSweetsBot.handlers.message_handlers.CartButtonHandler;
import com.github.AlexanderSobko.MatteoSweetsBot.services.BotUserService;
import com.github.AlexanderSobko.MatteoSweetsBot.services.OrderService;
import com.github.AlexanderSobko.MatteoSweetsBot.services.PatisserieService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
public class CartHandler extends BaseHandler {

    private final CartButtonHandler cartHandler;
    private String text;
    private boolean isPhoneNeeded;

    @Override
    public List<Object> handle(Update update) {
        String chatId = update.getCallbackQuery().getMessage().getChatId().toString();
        String[] data = update.getCallbackQuery().getData().split(" ");
        data[0] = "";
        Integer messageId = null;

        if (data[1].contains("Clear")) {
            orderService.clearLastOrder(chatId);
            messageId = update.getCallbackQuery().getMessage().getMessageId();
        } else if (data[1].contains("Finish")){
            isPhoneNeeded = false;
            text = orderService.finishOrder(chatId) +
                    "\n\nСпасибо за ваш заказ. В ближайшее время с вами свяжется продавец.\n";
            messageId = update.getCallbackQuery().getMessage().getMessageId();
        } else {
            String callbackData = patisserieService.addPatisserieToOrder(data, chatId);
            update.getCallbackQuery().setData(callbackData);
            return cartHandler.handle(update);
        }

        List<Object> messages = new ArrayList<>();
        messages.add(getMessage(chatId, null, messageId));
        return messages;
    }

    @Override
    protected BotApiMethod<? extends Serializable> getMessage(String chatId, String callbackData, Integer messageId) {
        return EditMessageText.builder()
                .text(text)
                .chatId(chatId)
                .messageId(messageId)
                .build();
    }

    public CartHandler(BotUserService botUserService,
                       OrderService orderService,
                       PatisserieService patisserieService,
                       CartButtonHandler cartHandler) {
        super(botUserService, orderService, patisserieService);
        this.cartHandler = cartHandler;
    }
}
