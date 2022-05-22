package com.github.AlexanderSobko.MatteoSweetsBot.handlers.message_handlers;

import com.github.AlexanderSobko.MatteoSweetsBot.handlers.BaseHandler;
import com.github.AlexanderSobko.MatteoSweetsBot.handlers.Handler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MessageHandler implements Handler {

    private Map<String, BaseHandler> messageHandlers;

    private final StartHandler startHandlerHandler;
    private final CartButtonHandler cartButtonHandler;
    private final OrderHistoryButtonHandler orderHistoryButtonHandler;
    private final DeliveryButtonHandler deliveryButtonHandler;
    private final HelpButtonHandler helpButtonHandler;
    private final FeedbackButtonHandler feedbackButtonHandler;
    private final CatalogButtonHandler catalogButtonHandler;
    private final DefaultMessageHandler defaultMessageHandler;

    @Override
    public List<Object> handle(Update update) {
        String text = update.getMessage().getText();
        String handlerName = messageHandlers.keySet().stream().filter(text::contains).findFirst().orElse("Default Message Handler");
        return messageHandlers.get(handlerName).handle(update);
    }

    @PostConstruct
    private void init() {
        messageHandlers = new HashMap<>() {{
            put("/start", startHandlerHandler);
            put("Корзина", cartButtonHandler);
            put("История заказов", orderHistoryButtonHandler);
            put("Способ доставки", deliveryButtonHandler);
            put("Помощь", helpButtonHandler);
            put("Отзывы", feedbackButtonHandler);
            put("Каталог", catalogButtonHandler);
            put("Default Message Handler", defaultMessageHandler);
        }};
    }

    @Autowired
    public MessageHandler(StartHandler startHandlerHandler,
                          CartButtonHandler cartButtonHandler,
                          OrderHistoryButtonHandler orderHistoryButtonHandler,
                          DeliveryButtonHandler deliveryButtonHandler,
                          HelpButtonHandler helpButtonHandler,
                          FeedbackButtonHandler feedbackButtonHandler,
                          CatalogButtonHandler catalogButtonHandler,
                          DefaultMessageHandler defaultMessageHandler) {
        this.startHandlerHandler = startHandlerHandler;
        this.cartButtonHandler = cartButtonHandler;
        this.orderHistoryButtonHandler = orderHistoryButtonHandler;
        this.deliveryButtonHandler = deliveryButtonHandler;
        this.helpButtonHandler = helpButtonHandler;
        this.feedbackButtonHandler = feedbackButtonHandler;
        this.catalogButtonHandler = catalogButtonHandler;
        this.defaultMessageHandler = defaultMessageHandler;
    }
}
