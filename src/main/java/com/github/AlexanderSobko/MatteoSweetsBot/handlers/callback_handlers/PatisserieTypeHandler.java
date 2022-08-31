package com.github.AlexanderSobko.MatteoSweetsBot.handlers.callback_handlers;

import com.github.AlexanderSobko.MatteoSweetsBot.enums.PatisserieType;
import com.github.AlexanderSobko.MatteoSweetsBot.handlers.BaseHandler;
import com.github.AlexanderSobko.MatteoSweetsBot.services.UserService;
import com.github.AlexanderSobko.MatteoSweetsBot.services.OrderService;
import com.github.AlexanderSobko.MatteoSweetsBot.services.PatisserieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.github.AlexanderSobko.MatteoSweetsBot.enums.PatisserieSubType.*;

@Component
public class PatisserieTypeHandler extends BaseHandler {

    private static final String CAKE_TEXT = "Какой тортик вам по душе?";
    private static final String CHOCO_TEXT = "Какой шоколад вы желаете?";

    @Override
    public List<Object> handle(Update update) {
        String chatId = update.getCallbackQuery().getMessage().getChatId().toString();
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
        String[] data = update.getCallbackQuery().getData().split(" ");
        data[0] = "PatisserieSubType";
        String callbackData = String.join(" ", data) + " ";

        List<Object> messages = new ArrayList<>();
        messages.add(new DeleteMessage(chatId, messageId));
        messages.add(getMedia(chatId, callbackData));
        messages.add(getMessage(chatId, callbackData, null));
        return messages;
    }

    @Override
    protected BotApiMethod<? extends Serializable> getMessage(String chatId, String callbackData, Integer messageId) {
        if (callbackData.contains(PatisserieType.CAKE.name()))
            return SendMessage.builder()
                    .text(CAKE_TEXT)
                    .chatId(chatId)
                    .replyMarkup(
                        InlineKeyboardMarkup.builder().keyboard(List.of(
                                List.of(
                                    InlineKeyboardButton.builder().text("Муссовый").callbackData(callbackData + MOUSSE_CAKE).build(),
                                    InlineKeyboardButton.builder().text("Бисквитный").callbackData(callbackData + BISCUIT_CAKE).build())))
                        .build())
                    .build();
        else
            return SendMessage.builder()
                    .text(CHOCO_TEXT)
                    .chatId(chatId)
                    .replyMarkup(
                        InlineKeyboardMarkup.builder().keyboard(List.of(
                                List.of(
                                        InlineKeyboardButton.builder().text("Белый").callbackData(callbackData + WHITE_CHOCOLATE).build(),
                                        InlineKeyboardButton.builder().text("Молочный").callbackData(callbackData + MILK_CHOCOLATE).build()),
                                List.of(
                                        InlineKeyboardButton.builder().text("Карамелизированный").callbackData(callbackData + CARAMEL_CHOCOLATE).build(),
                                        InlineKeyboardButton.builder().text("Черный").callbackData(callbackData + BLACK_CHOCOLATE).build())))
//                                Arrays.asList(
//                                        InlineKeyboardButton.builder().callbackData("Выбрать готовое изделие").callbackData("PickChocolate").build()))
                        .build())
                    .build();
    }

    private SendMediaGroup getMedia(String chatId, String callbackData) {
        if (callbackData.contains(PatisserieType.CHOCOLATE.name()))
            return SendMediaGroup.builder()
                .chatId(chatId)
                .medias(List.of(
                        InputMediaPhoto.builder().caption("Муссовый торт привет\n").media("AgACAgIAAxkBAAN7YfL3cj9d3khX0PMqFkjLRoxL6KwAAhS6MRsnb5lL2cvJqmhFxhABAAMCAAN5AAMjBA").build(),
                        InputMediaPhoto.builder().caption("Муссовый торт привет\n").media("AgACAgIAAxkBAAODYfL3fJa83W_NHQEVWqIeeMf-GW8AAhy6MRsnb5lLmV-ZaVEL4UkBAAMCAAN5AAMjBA").build(),
                        InputMediaPhoto.builder().caption("Муссовый торт привет\n").media("AgACAgIAAxkBAAOBYfL3ebY2y4s1M78PLLUOXZyA4jUAAhq6MRsnb5lLLlwhrulG0p8BAAMCAAN5AAMjBA").build(),
                        InputMediaPhoto.builder().caption("Муссовый торт привет\n").media("AgACAgIAAxkBAAN9YfL3cyjbQTyqAAFAXLFByZ0qLAU6AAIWujEbJ2-ZS7eyqiRe7CNZAQADAgADeQADIwQ").build(),
                        InputMediaPhoto.builder().caption("Муссовый торт привет\n").media("AgACAgIAAxkBAAN-YfL3dVjHZh2o9Wl0lhCAUAL4ZO4AAhe6MRsnb5lLeeyxfcYeW6gBAAMCAAN5AAMjBA").build(),
                        InputMediaPhoto.builder().caption("Муссовый торт привет\n").media("AgACAgIAAxkBAAOEYfL3fR0wrjl5roAhxVV1WmJPd6QAAh26MRsnb5lL6oS-w_8ImAMBAAMCAAN5AAMjBA").build()))
                .build();
        else
            return SendMediaGroup.builder()
                    .chatId(chatId)
                    .medias(
                            List.of(
                                InputMediaPhoto.builder().caption("Муссовый торт привет\n").media("AgACAgIAAxkBAAO1YfL9eUgnPKYyiy_Kbo-wysNbmVMAAj-6MRsnb5lL_FMrAwzhoKMBAAMCAAN5AAMjBA").build(),
                                InputMediaPhoto.builder().caption("Муссовый торт привет\n").media("AgACAgIAAxkBAAO3YfL9erw3aEn4is8-s_p9yQSryG0AAkG6MRsnb5lLPCRs4DmiiJEBAAMCAAN5AAMjBA").build(),
                                InputMediaPhoto.builder().caption("Муссовый торт привет\n").media("AgACAgIAAxkBAAPCYfL9gCoPohei_zTbylybqsJwI3UAAkq6MRsnb5lL1xlDtSxQmAkBAAMCAAN5AAMjBA").build(),
                                InputMediaPhoto.builder().caption("Муссовый торт привет\n").media("AgACAgIAAxkBAAPBYfL9gIVCazt55AOYyHurU5UjzS4AAjC6MRsnb5lLc-XWSXCnVYIBAAMCAAN5AAMjBA").build(),
                                InputMediaPhoto.builder().caption("Муссовый торт привет\n").media("AgACAgIAAxkBAAPAYfL9f6HCGqsqU5msNf5hnSd_jWEAAkm6MRsnb5lLWybmMrX0R3MBAAMCAAN5AAMjBA").build(),
                                InputMediaPhoto.builder().caption("Муссовый торт привет\n").media("AgACAgIAAxkBAAOTYfL5-BrIyi1LWPakS3NvNAzZrJcAAiu6MRsnb5lLW9gjfX2txBUBAAMCAAN5AAMjBA").build()))
                    .build();
    }

    @Autowired
    public PatisserieTypeHandler(UserService UserService,
                                 OrderService orderService,
                                 PatisserieService patisserieService) {
        super(UserService, orderService, patisserieService);
    }
}
