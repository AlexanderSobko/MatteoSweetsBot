package com.github.AlexanderSobko.MatteoSweetsBot.handlers;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public interface Handler {

    List<Object> handle(Update update);
}
