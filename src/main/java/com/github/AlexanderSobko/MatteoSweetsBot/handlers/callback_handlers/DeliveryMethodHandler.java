package com.github.AlexanderSobko.MatteoSweetsBot.handlers.callback_handlers;

import com.github.AlexanderSobko.MatteoSweetsBot.entities.BotUser;
import com.github.AlexanderSobko.MatteoSweetsBot.handlers.BaseHandler;
import com.github.AlexanderSobko.MatteoSweetsBot.services.BotUserService;
import com.github.AlexanderSobko.MatteoSweetsBot.services.OrderService;
import com.github.AlexanderSobko.MatteoSweetsBot.services.PatisserieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
public class DeliveryMethodHandler extends BaseHandler {

    @Override
    public List<Object> handle(Update update) {
        String chatId = update.getCallbackQuery().getFrom().getId().toString();
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
        String callbackData = update.getCallbackQuery().getData();
        String text = "Укажите адресс доставки ";
        BotUser botUser = botUserService.getUserById(chatId);

        if (callbackData.contains("Post")) {
            botUser.setDeliveryMethod("\"Почтой\"");
            text = text + "почтой:\n";
        } else if (callbackData.contains("Carrier")) {
            botUser.setDeliveryMethod("\"Курьером\"");
            text = text + "курьером:\n";
        } else if (callbackData.contains("Pickup")) {
            botUser.setDeliveryMethod("\"Самовывоз\"");
            botUser.setDeliveryAddress("г. Краснодар, ул. 40 лет Победы 33/6");
            text = """
                    Способ доставки успешно изменен.
                    Текущий метод доставки:
                    "Самовывоз"
                    Адресс: г. Краснодар, ул. 40 лет Победы 33/6""";
        }
        botUser.setWaiting(true);
        botUserService.save(botUser);

        List<Object> messages = new ArrayList<>();
        messages.add(getMessage(chatId, text, messageId));
        return messages;
    }

    @Override
    protected BotApiMethod<? extends Serializable> getMessage(String chatId, String callbackData, Integer messageId) {
        return EditMessageText.builder()
                .text(callbackData)
                .chatId(chatId)
                .messageId(messageId)
                .replyMarkup(
                    callbackData.contains("\"Самовывоз\"") ?
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
    public DeliveryMethodHandler(BotUserService botUserService,
                                 OrderService orderService,
                                 PatisserieService patisserieService) {
        super(botUserService, orderService, patisserieService);
    }
}
