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
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.github.AlexanderSobko.MatteoSweetsBot.enums.PatisserieSubType.BISCUIT_CAKE;
import static com.github.AlexanderSobko.MatteoSweetsBot.enums.PatisserieSubType.MOUSSE_CAKE;

@Component
public class PatisserieSubTypeHandler extends BaseHandler {

    private static final String CHOCO_TYPE_TEXT = """
            Выберите размер шоколадки:
            Большая, 150-200 гр.
            Маленькая, 100 - 120 гр.""";
    private static final String BISCUIT_FLAVOR_TEXT = """
            Вы выбрали бисквитный торт.
            Выберите начинку:
            1. "Молочный пломбир с карамелизированным бананом"
            Сливочные тонкие коржи на сгущенном молоке, крем на основе сливочного сыра и сливок, карамелизированные бананы с пряностями
            2. "Классический ванильный"
            Воздушные ванильный бисквит, крем на сливочном сыре
            3."Шоколадные тропики"
            Шоколадный бисквит, ганаш манго- маракуя, шоколадный крем на основе сливочного сыра
            4. "Рафаэлло"
            Ванильный бисквит, сливочный крем, хрустящий слой с миндалём
            5. "Красный бархат с вишней"
            Шоколадный коржи, сливочный крем, кремю из вишни
            6. "Малиновый бабл"
            Ванильный бисквит, малиновый курд, безе, карамелизированный мусс, сливочный крем
            7. "Сникерс"
            Шоколадный бисквит, карамель, солёный арахис, безе
            8. "Кофейная груша"
            Шоколадный бисквит, карамелизированная груша, шоколадный крем, кофейный мусс
            9. "Хрустящая вишня"
            Шоколадный бисквит, вишня в сиропе, хрустящий слой, ванильный крем""";
    private static final String MOUSSE_FLAVOR_TEXT = """
            Вы выбрали муссовый торт.
            Выберите начинку:
            1."Медовый апельсин" . Медовые коржи, имбирное желе, апельсиновый мусс, сливочный мусс.
            2."Ананасовый сорбет" . Ванильный бисквит, ореховое пралине, ананасовая прослойка, сливочно- творожный мусс
            3. "Дабл_эпл" .Белый бисквит, шоколадно- ореховая прослойка, яблочный центр, яблочный мусс
            4. Три шоколада:  шоколадный бисквит( 3 вида мусса : на белом, молочном и темном шоколаде)
            5. "Клубничный Шейк" .Шоколадный бисквит, кофейно- шоколадная прослойка, клубнично- базиликовая прослойка, мусс на сливочном сыре
            6. "Лимонный блюз" .Песочный корж, лимонно- шоколадная начинка, безе, мусс на молочном шоколаде и йогурте
            7."Бейлис" .Шоколад- кофейная прослойка, воздушный бисквит, сливочно- сырный мусс с бейлисом.""";

    @Override
    public List<Object> handle(Update update) {
        String chatId = update.getCallbackQuery().getMessage().getChatId().toString();
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
        String[] data = update.getCallbackQuery().getData().split(" ");
        data[0] = data[1].equals(PatisserieType.CHOCOLATE.name()) ? "PatisserieSize" : "PatisserieFlavorHandler";
        String callbackData = String.join(" ", data) + " ";

        List<Object> messages = new ArrayList<>();

        if (callbackData.contains(PatisserieType.CAKE.name()))
            messages.add(new DeleteMessage(chatId, messageId));

        messages.add(getMedia(chatId, callbackData));
        messages.add(getMessage(chatId, callbackData, messageId));
        return messages;
    }

    @Override
    protected BotApiMethod<? extends Serializable> getMessage(String chatId, String callbackData, Integer messageId) {
        if (callbackData.contains(PatisserieType.CHOCOLATE.name())) {
            return EditMessageText.builder()
                    .chatId(chatId)
                    .text(CHOCO_TYPE_TEXT)
                    .replyMarkup(
                            InlineKeyboardMarkup.builder().keyboard(List.of(
                                    List.of(
                                            InlineKeyboardButton.builder().text("Большая").callbackData(callbackData + "Big").build(),
                                            InlineKeyboardButton.builder().text("Маленькая").callbackData(callbackData + "Small").build())))
                                    .build())
                    .messageId(messageId)
                    .build();
        } else {
            String messageText = callbackData.contains(BISCUIT_CAKE.name()) ? BISCUIT_FLAVOR_TEXT : MOUSSE_FLAVOR_TEXT;
            return SendMessage.builder()
                    .chatId(chatId)
                    .text(messageText)
                    .replyMarkup(
                            InlineKeyboardMarkup.builder().keyboard(List.of(
                                    List.of(
                                            InlineKeyboardButton.builder().text("1").callbackData(callbackData + 1).build(),
                                            InlineKeyboardButton.builder().text("2").callbackData(callbackData + 2).build(),
                                            InlineKeyboardButton.builder().text("3").callbackData(callbackData + 3).build()),
                                    List.of(
                                            InlineKeyboardButton.builder().text("4").callbackData(callbackData + 4).build(),
                                            InlineKeyboardButton.builder().text("5").callbackData(callbackData + 5).build(),
                                            InlineKeyboardButton.builder().text("6").callbackData(callbackData + 6).build()),
                                    callbackData.contains(BISCUIT_CAKE.name()) ?
                                            List.of(
                                                    InlineKeyboardButton.builder().text("7").callbackData(callbackData + 7).build(),
                                                    InlineKeyboardButton.builder().text("8").callbackData(callbackData + 8).build(),
                                                    InlineKeyboardButton.builder().text("9").callbackData(callbackData + 9).build())
                                            :
                                            List.of(
                                                    InlineKeyboardButton.builder().text("7").callbackData(callbackData + 7).build())))
                                    .build())
                    .build();
        }
    }

    private SendMediaGroup getMedia(String chatId, String callbackData) {
        if (callbackData.contains(BISCUIT_CAKE.name()))
            return SendMediaGroup.builder()
                    .chatId(chatId)
                    .medias(
                            List.of(
                                    InputMediaPhoto.builder().caption("Муссовый торт привет\n").media("AgACAgIAAxkBAAOhYfL8Ge242LxUg2WQ7Zfq28QuBSUAAje6MRsnb5lL67aU2w0_L5sBAAMCAAN5AAMjBA").build(),
                                    InputMediaPhoto.builder().caption("Муссовый торт привет\n").media("AgACAgIAAxkBAAOiYfL8GvaOc6eYlwABhvRWBOmoaGp_AAI4ujEbJ2-ZS1dIvzCejiN6AQADAgADeQADIwQ").build(),
                                    InputMediaPhoto.builder().caption("Муссовый торт привет\n").media("AgACAgIAAxkBAAOjYfL8G02OoPA-j_PcZbpJeq-xHKEAAjm6MRsnb5lLMB8L_aNirEMBAAMCAAN5AAMjBA").build(),
                                    InputMediaPhoto.builder().caption("Муссовый торт привет\n").media("AgACAgIAAxkBAAOkYfL8HKtOTyRIwZZli4u6YvJLq0kAAjq6MRsnb5lLYY03RPcXlzcBAAMCAAN5AAMjBA").build(),
                                    InputMediaPhoto.builder().caption("Муссовый торт привет\n").media("AgACAgIAAxkBAAOlYfL8HchlhMFoiv-tOtB1zK7Oha8AAju6MRsnb5lLmUOlzJBFSi8BAAMCAAN5AAMjBA").build(),
                                    InputMediaPhoto.builder().caption("Муссовый торт привет\n").media("AgACAgIAAxkBAAOmYfL8HmQNlEItKFN5K5UPDy4wMKIAAjy6MRsnb5lLZ_a35BoPGhQBAAMCAAN5AAMjBA").build()))
                    .build();
        else if (callbackData.contains(MOUSSE_CAKE.name()))
            return SendMediaGroup.builder()
                    .chatId(chatId)
                    .medias(
                            List.of(
                                    InputMediaPhoto.builder().caption("Муссовый торт привет\n").media("AgACAgIAAxkBAAOQYfL59oEB45_2onoNbQR7D08emckAAii6MRsnb5lL8VQ5q9knEPABAAMCAAN5AAMjBA").build(),
                                    InputMediaPhoto.builder().caption("Муссовый торт привет\n").media("AgACAgIAAxkBAAOcYfL5_sOGCl8tQl8U7MG9UJu9yUUAAjS6MRsnb5lLT1BJ375uckMBAAMCAAN5AAMjBA").build(),
                                    InputMediaPhoto.builder().caption("Муссовый торт привет\n").media("AgACAgIAAxkBAAObYfL5_anDEvhGw8jfaDRUoqShWyoAAjO6MRsnb5lL13GAfrKSPpoBAAMCAAN5AAMjBA").build(),
                                    InputMediaPhoto.builder().caption("Муссовый торт привет\n").media("AgACAgIAAxkBAAOaYfL5_IMUCWPchYRqljekcg_LOhkAAjK6MRsnb5lLdAExRh-E1W0BAAMCAAN5AAMjBA").build(),
                                    InputMediaPhoto.builder().caption("Муссовый торт привет\n").media("AgACAgIAAxkBAAOaYfL5_IMUCWPchYRqljekcg_LOhkAAjK6MRsnb5lLdAExRh-E1W0BAAMCAAN5AAMjBA").build(),
                                    InputMediaPhoto.builder().caption("Муссовый торт привет\n").media("AgACAgIAAxkBAAOYYfL5-5hZ-N4GgV76neRjyEXdZyQAAjC6MRsnb5lLc-XWSXCnVYIBAAMCAAN5AAMjBA").build()))
                    .build();
        else
            return null;
    }

    @Autowired
    public PatisserieSubTypeHandler(UserService UserService,
                                    OrderService orderService,
                                    PatisserieService patisserieService) {
        super(UserService, orderService, patisserieService);
    }
}
