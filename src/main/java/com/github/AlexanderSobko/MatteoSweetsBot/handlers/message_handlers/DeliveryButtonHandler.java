package com.github.AlexanderSobko.MatteoSweetsBot.handlers.message_handlers;

import com.github.AlexanderSobko.MatteoSweetsBot.entities.BotUser;
import com.github.AlexanderSobko.MatteoSweetsBot.handlers.BaseHandler;
import com.github.AlexanderSobko.MatteoSweetsBot.services.BotUserService;
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
public class DeliveryButtonHandler extends BaseHandler {

    private static final String DELIVERY_TEXT = """
            На данный момент доступны три способа доставки:
            1. Почтой.
            2. Доставка курьером/такси (Действует только в Краснодаре).
            3. Самовывоз (ул. 40 лет Победы 33/6).

            Текущий метод доставки:
            %s
            Адресс: %s""";

    @Override
    public List<Object> handle(Update update) {
        String chatId = update.getMessage().getChatId().toString();

        BotUser botUser = botUserService.getUserById(chatId);
        String deliveryMethod = botUser.getDeliveryMethod();
        String deliveryAddress = botUser.getDeliveryAddress();

        List<Object> messages = new ArrayList<>();
        messages.add(getMessage(chatId, DELIVERY_TEXT.formatted(deliveryMethod, deliveryAddress), null));
        return messages;
    }

    @Override
    protected BotApiMethod<? extends Serializable> getMessage(String chatId, String callbackData, Integer messageId) {
        return SendMessage.builder()
                .text(callbackData)
                .chatId(chatId)
                .replyMarkup(
                    InlineKeyboardMarkup.builder().keyboard(
                            List.of(
                                    List.of(InlineKeyboardButton.builder().text("Указать адресс для отправки почтой").callbackData("DeliveryMethod Post").build()),
                                    List.of(InlineKeyboardButton.builder().text("Указать адресс для доставки курьером").callbackData("DeliveryMethod Carrier").build()),
                                    List.of(InlineKeyboardButton.builder().text("Самовывоз").callbackData("DeliveryMethod Pickup").build())))
                    .build())
                .replyToMessageId(messageId)
                .build();
    }

    @Autowired
    public DeliveryButtonHandler(BotUserService botUserService,
                                 OrderService orderService,
                                 PatisserieService patisserieService) {
        super(botUserService, orderService, patisserieService);
    }
}
