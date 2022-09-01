package com.github.AlexanderSobko.MatteoSweetsBot.handlers.message_handlers;

import com.github.AlexanderSobko.MatteoSweetsBot.models.entities.User;
import com.github.AlexanderSobko.MatteoSweetsBot.handlers.BaseHandler;
import com.github.AlexanderSobko.MatteoSweetsBot.services.UserService;
import com.github.AlexanderSobko.MatteoSweetsBot.services.OrderService;
import com.github.AlexanderSobko.MatteoSweetsBot.services.PatisserieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
public class DefaultMessageHandler extends BaseHandler {

    private static final String DELIVERY_METHOD = """
            Способ доставки успешно изменен.
            Текущий метод доставки:
            %s
            Адресс: %s""";
    private boolean isAddress;

    @Override
    public List<Object> handle(Update update) {
        isAddress = false;
        String chatId = update.getMessage().getFrom().getId().toString();
        String text = update.getMessage().getText();
        User User = UserService.getUserByTelegramId(chatId);

        if (UserService.getUserStatus(chatId) != null) {
            User.setDeliveryAddress(text);
            UserService.setUserStatus(chatId, null);
            UserService.save(User);
            text = DELIVERY_METHOD.formatted(User.getDeliveryMethod(), text);
            isAddress = true;
        }

        List<Object> messages = new ArrayList<>();
        messages.add(getMessage(chatId, text, null));
        return messages;
    }

    @Override
    protected BotApiMethod<? extends Serializable> getMessage(String chatId, String callbackData, Integer messageId) {
        return SendMessage.builder()
                .text(callbackData)
                .chatId(chatId)
                .replyMarkup(
                    isAddress ?
                        InlineKeyboardMarkup.builder().keyboard(
                                List.of(
                                    List.of(InlineKeyboardButton.builder().text("Указать адресс для отправки почтой").callbackData("DeliveryMethod Post").build()),
                                    List.of(InlineKeyboardButton.builder().text("Указать адресс для доставки курьером").callbackData("DeliveryMethod Carrier").build()),
                                    List.of(InlineKeyboardButton.builder().text("Самовывоз").callbackData("DeliveryMethod Pickup").build())))
                        .build()
                    :
                     null)
                .build();
    }

    @Autowired
    public DefaultMessageHandler(UserService UserService,
                                 OrderService orderService,
                                 PatisserieService patisserieService) {
        super(UserService, orderService, patisserieService);
    }
}
