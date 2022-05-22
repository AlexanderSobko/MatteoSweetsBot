package com.github.AlexanderSobko.MatteoSweetsBot.handlers.message_handlers;

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
public class FeedbackButtonHandler extends BaseHandler {

    private static final String FEEDBACK_TEXT = "Отзывы наших клиентов:";

    @Override
    public List<Object> handle(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        List<Object> messages = new ArrayList<>();
        messages.add(getMessage(chatId, FEEDBACK_TEXT, null));
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
                                    List.of(
                                        InlineKeyboardButton.builder().text("Оставить отзыв").callbackData("feedback").build())))
                    .build())
                .replyToMessageId(messageId)
                .build();
    }

    @Autowired
    public FeedbackButtonHandler(BotUserService botUserService,
                                 OrderService orderService,
                                 PatisserieService patisserieService) {
        super(botUserService, orderService, patisserieService);
    }
}
