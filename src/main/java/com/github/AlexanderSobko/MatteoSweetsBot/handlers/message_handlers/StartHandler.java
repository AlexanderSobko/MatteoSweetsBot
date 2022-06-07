package com.github.AlexanderSobko.MatteoSweetsBot.handlers.message_handlers;

import com.github.AlexanderSobko.MatteoSweetsBot.entities.BotUser;
import com.github.AlexanderSobko.MatteoSweetsBot.handlers.BaseHandler;
import com.github.AlexanderSobko.MatteoSweetsBot.services.BotUserService;
import com.github.AlexanderSobko.MatteoSweetsBot.services.OrderService;
import com.github.AlexanderSobko.MatteoSweetsBot.services.PatisserieService;
import com.github.AlexanderSobko.MatteoSweetsBot.services.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class StartHandler extends BaseHandler {

    private static final String START_TEXT = "Добро пожаловать!";
    private final RestService restService;

    @Override
    public List<Object> handle(Update update) {
        BotUser user = botUserService.userToBotUser(update.getMessage().getFrom());
        if (botUserService.getUserById(user.getTelegramId()) == null) {
            user.setPhoto(restService.getUserPhoto(user.getTelegramId()));
            botUserService.save(user);
        }

        List<Object> messages = new ArrayList<>();
        messages.add(getMessage(user.getTelegramId(), START_TEXT, null));
        return messages;
    }

    @Override
    protected BotApiMethod<? extends Serializable> getMessage(String chatId, String callbackData, Integer messageId) {
        return SendMessage.builder()
                .text(callbackData)
                .replyMarkup(ReplyKeyboardMarkup.builder()
                        .keyboard(
                                List.of(
                                        new KeyboardRow(Arrays.asList(new KeyboardButton("Каталог"),new KeyboardButton("Корзина"))),
                                        new KeyboardRow(Arrays.asList(new KeyboardButton("Способ доставки"),new KeyboardButton("История заказов"))),
                                        new KeyboardRow(Arrays.asList(new KeyboardButton("Помощь"), new KeyboardButton("Отзывы")))))
                        .resizeKeyboard(true)
                        .oneTimeKeyboard(true)
                        .selective(true)
                        .build())
                .chatId(chatId)
                .replyToMessageId(messageId)
                .build();
    }

    @Autowired
    public StartHandler(BotUserService botUserService,
                        OrderService orderService,
                        PatisserieService patisserieService, RestService restService) {
        super(botUserService, orderService, patisserieService);
        this.restService = restService;
    }
}
