package com.github.AlexanderSobko.MatteoSweetsBot.core;

import com.github.AlexanderSobko.MatteoSweetsBot.handlers.UpdateHandler;
import com.github.AlexanderSobko.MatteoSweetsBot.services.RestService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class TelegramBot extends TelegramLongPollingBot {

    @Autowired
    private UpdateHandler updateHandler;

    @Getter
    @Value("${BOT_USERNAME}")
    private String botUsername;

    @Getter
    @Value("${BOT_TOKEN}")
    private String botToken;

    @Override
    @SneakyThrows
    public void onUpdateReceived(Update update) {
        List<Object> messages = updateHandler.handle(update);
        for (Object message : messages) {
            if (message instanceof SendMessage)
                execute((SendMessage) message);
            else if (message instanceof SendMediaGroup)
                execute((SendMediaGroup) message);
            else if (message instanceof EditMessageText)
                execute((EditMessageText) message);
            else if (message instanceof DeleteMessage)
                execute((DeleteMessage) message);
        }

    }

}
