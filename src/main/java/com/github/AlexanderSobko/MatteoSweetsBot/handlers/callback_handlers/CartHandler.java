package com.github.AlexanderSobko.MatteoSweetsBot.handlers.callback_handlers;

import com.github.AlexanderSobko.MatteoSweetsBot.handlers.BaseHandler;
import com.github.AlexanderSobko.MatteoSweetsBot.handlers.message_handlers.CartButtonHandler;
import com.github.AlexanderSobko.MatteoSweetsBot.services.BotUserService;
import com.github.AlexanderSobko.MatteoSweetsBot.services.OrderService;
import com.github.AlexanderSobko.MatteoSweetsBot.services.PatisserieService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
public class CartHandler extends BaseHandler {

    private final CartButtonHandler cartHandler;
    private final String ADMIN_ID = "5060682407";
    private String text;
    private String notice;

    @Override
    public List<Object> handle(Update update) {
        List<Object> messages = new ArrayList<>();
        String chatId = update.getCallbackQuery().getMessage().getChatId().toString();
        String[] data = update.getCallbackQuery().getData().split(" ");
        data[0] = "";
        Integer messageId = null;

        if (data[1].contains("Clear")) {
            orderService.clearLastOrder(chatId);
            return cartHandler.handle(update);
        } else if (data[1].contains("Finish")) {
            String orderData = orderService.finishOrder(chatId);
            text = orderData +
                    "\n\nСпасибо за ваш заказ. В ближайшее время с вами свяжется продавец.\n";
            notice = orderData + "\n" + botUserService.getUserById(chatId).toString();
            messageId = update.getCallbackQuery().getMessage().getMessageId();
            messages.add(getMessage(ADMIN_ID, null, null));
        } else {
            String callbackData = patisserieService.addPatisserieToOrder(data, chatId);
            update.getCallbackQuery().setData(callbackData);
            return cartHandler.handle(update);
        }


        messages.add(getMessage(chatId, null, messageId));
        return messages;
    }

    @Override
    protected BotApiMethod<? extends Serializable> getMessage(String chatId, String callbackData, Integer messageId) {
        return SendMessage.builder()
                .text(notice)
                .chatId(chatId)
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
