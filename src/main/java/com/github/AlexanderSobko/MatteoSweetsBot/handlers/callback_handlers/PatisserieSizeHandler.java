package com.github.AlexanderSobko.MatteoSweetsBot.handlers.callback_handlers;

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
public class PatisserieSizeHandler extends BaseHandler {

    private static final String CHOCO_SIZE_TEXT = """
            Вы выбрали %s
            Ввиду огромного разнообразия начинки и вышей безграничной фантазии,
            Начинка и дизайн обсуждаются лично.
            """;
    private String text;

    @Override
    public List<Object> handle(Update update) {
        String chatId = update.getCallbackQuery().getFrom().getId().toString();
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
        String[] data = update.getCallbackQuery().getData().split(" ");
        data[0] = "";
        String callbackData = String.join(" ", data);

        text = patisserieService.parsePatisserie(data).toString().toLowerCase();
        text = text.replace("ая", "ую").replace("плитка", "плитку");
        text = text.substring(2).replace(".", "!");


        List<Object> messages = new ArrayList<>();
        messages.add(getMessage(chatId, callbackData, messageId));
        return messages;
    }

    @Override
    protected BotApiMethod<? extends Serializable> getMessage(String chatId, String callbackData, Integer messageId) {
        return EditMessageText.builder()
                .text(CHOCO_SIZE_TEXT.formatted(this.text))
                .chatId(chatId)
                .replyMarkup(
                        InlineKeyboardMarkup.builder().keyboard(List.of(
                                List.of(
                                        InlineKeyboardButton.builder().text("Добавить в корзину").callbackData("CartHandler" + callbackData).build(),
                                        InlineKeyboardButton.builder().text("Связаться с продавцом.").callbackData("ConnectOperator").build())))
                                .build())
                .messageId(messageId)
                .build();
    }

    @Autowired
    public PatisserieSizeHandler(BotUserService botUserService,
                                 OrderService orderService,
                                 PatisserieService patisserieService) {
        super(botUserService, orderService, patisserieService);
    }
}
