package com.github.AlexanderSobko.MatteoSweetsBot.handlers;

import com.github.AlexanderSobko.MatteoSweetsBot.handlers.callback_handlers.CallbackHandler;
import com.github.AlexanderSobko.MatteoSweetsBot.handlers.message_handlers.MessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
public class UpdateHandler implements Handler {

    private final MessageHandler messageHandler;
    private final CallbackHandler callbackHandler;

    @Override
    public List<Object> handle(Update update) {
        List<Object> messages;
        if (update.hasCallbackQuery())
            messages = callbackHandler.handle(update);
        else
            messages = messageHandler.handle(update);
        return messages;
    }

    @Autowired
    public UpdateHandler(MessageHandler messageHandler, CallbackHandler callbackHandler) {
        this.messageHandler = messageHandler;
        this.callbackHandler = callbackHandler;
    }
}
