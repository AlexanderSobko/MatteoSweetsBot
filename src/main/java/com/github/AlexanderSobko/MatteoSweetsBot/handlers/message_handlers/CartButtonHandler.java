package com.github.AlexanderSobko.MatteoSweetsBot.handlers.message_handlers;

import com.github.AlexanderSobko.MatteoSweetsBot.handlers.BaseHandler;
import com.github.AlexanderSobko.MatteoSweetsBot.services.BotUserService;
import com.github.AlexanderSobko.MatteoSweetsBot.services.OrderService;
import com.github.AlexanderSobko.MatteoSweetsBot.services.PatisserieService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CartButtonHandler extends BaseHandler {

    private static final String CART_IS_EMPTY = "Ваша корзина пуста(\n";
    private boolean isEmpty;
    private String text;

    @Override
    public List<Object> handle(Update update) {
        String chatId;
        Integer messageId = null;
        String lastAdded = "";
        if (update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getMessage().getChatId().toString();
            String data = update.getCallbackQuery().getData();
            lastAdded = "В ваш заказ добавлено:\n%s\n".formatted(data);
            messageId = update.getCallbackQuery().getMessage().getMessageId();
        } else {
            chatId = update.getMessage().getChatId().toString();
        }

        isEmpty = orderService.isLastOrderEmpty(chatId);

        if (isEmpty) {
            text = CART_IS_EMPTY;
        } else {
            text = lastAdded + "В вашей корзине:\n" + orderService.getLastOrderInfo(chatId);
        }

        List<Object> messages = new ArrayList<>();
        messages.add(getMessage(chatId, null, messageId));
        return messages;
    }

    @Override
    protected BotApiMethod<? extends Serializable> getMessage(String chatId, String callbackData, Integer messageId) {
        InlineKeyboardMarkup markup1 = InlineKeyboardMarkup.builder().keyboard(List.of(
                List.of(
                        InlineKeyboardButton.builder().text("Каталог").callbackData("CatalogHandler").build()),
                List.of(
                        InlineKeyboardButton.builder().text("Очистить корзину").callbackData("CartHandler Clear").build(),
                        InlineKeyboardButton.builder().text("Завершить заказ").callbackData("CartHandler Finish").build()))).build();
        InlineKeyboardMarkup markup2 = InlineKeyboardMarkup.builder().keyboard(List.of(
                List.of(
                        InlineKeyboardButton.builder().text("Каталог").callbackData("CatalogHandler").build()))).build();
        if (messageId == null)
            return SendMessage.builder()
                    .chatId(chatId)
                    .text(text)
                    .replyMarkup(isEmpty ? markup2 : markup1)
                    .build();
        else
            return EditMessageText.builder()
                    .chatId(chatId)
                    .messageId(messageId)
                    .text(text)
                    .replyMarkup(isEmpty ? markup2 : markup1)
                    .build();
    }

    @Autowired
    public CartButtonHandler(BotUserService botUserService,
                             OrderService orderService,
                             PatisserieService patisserieService) {
        super(botUserService, orderService, patisserieService);
    }
}
