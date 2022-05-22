package com.github.AlexanderSobko.MatteoSweetsBot.handlers.message_handlers;

import com.github.AlexanderSobko.MatteoSweetsBot.handlers.BaseHandler;
import com.github.AlexanderSobko.MatteoSweetsBot.models.PatisserieType;
import com.github.AlexanderSobko.MatteoSweetsBot.services.BotUserService;
import com.github.AlexanderSobko.MatteoSweetsBot.services.OrderService;
import com.github.AlexanderSobko.MatteoSweetsBot.services.PatisserieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
public class CatalogButtonHandler extends BaseHandler {

    private static final String CATALOG_TEXT = "      Каталог\n" +
            "Какой вид изделия вы желаете?";

    @Override
    public List<Object> handle(Update update) {
        String chatId;
        Integer messageId = null;

        if (update.hasCallbackQuery()) {
            chatId =update.getCallbackQuery().getMessage().getChatId().toString();
            messageId =update.getCallbackQuery().getMessage().getMessageId();
        } else
            chatId = update.getMessage().getChatId().toString();

        List<Object> messages = new ArrayList<>();
        messages.add(getMessage(chatId, null, messageId));
        return messages;
    }

    @Override
    protected BotApiMethod<? extends Serializable> getMessage(String chatId, String callbackData, Integer messageId) {
        if (messageId == null)
            return SendMessage.builder()
                    .text(CATALOG_TEXT)
                    .chatId(chatId)
                    .replyMarkup(
                            InlineKeyboardMarkup.builder().keyboard(List.of(
                                    List.of(
                                            InlineKeyboardButton.builder().text("Шоколад").callbackData("PatisserieType " + PatisserieType.CHOCOLATE).build(),
                                            InlineKeyboardButton.builder().text("Торт").callbackData("PatisserieType " + PatisserieType.CAKE).build())))
                                    .build())
                    .replyToMessageId(messageId)
                    .build();
        else
            return EditMessageText.builder()
                    .text(CATALOG_TEXT)
                    .chatId(chatId)
                    .replyMarkup(
                            InlineKeyboardMarkup.builder().keyboard(List.of(
                                    List.of(
                                            InlineKeyboardButton.builder().text("Шоколад").callbackData("PatisserieType " + PatisserieType.CHOCOLATE).build(),
                                            InlineKeyboardButton.builder().text("Торт").callbackData("PatisserieType " + PatisserieType.CAKE).build())))
                                    .build())
                    .messageId(messageId)
                    .build();
    }

    private SendMediaGroup getMedia(String chatId) {
        return null;
    }

    @Autowired
    public CatalogButtonHandler(BotUserService botUserService,
                                OrderService orderService,
                                PatisserieService patisserieService) {
        super(botUserService, orderService, patisserieService);
    }
}
