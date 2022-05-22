package com.github.AlexanderSobko.MatteoSweetsBot.handlers.callback_handlers;

import com.github.AlexanderSobko.MatteoSweetsBot.handlers.Handler;
import com.github.AlexanderSobko.MatteoSweetsBot.handlers.message_handlers.CatalogButtonHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CallbackHandler implements Handler {

    private Map<String, Handler> callBackHandlers;

    private final PatisserieTypeHandler patisserieTypeHandler;
    private final CartHandler cartHandler;
    private final ConnectOperatorHandler connectOperatorHandler;
    private final PatisserieSubTypeHandler patisserieSubTypeHandler;
    private final PatisserieFlavorHandler patisserieFlavorHandler;
    private final DeliveryMethodHandler deliveryMethodHandler;
    private final PatisserieSizeHandler patisserieSizeHandler;
    private final CatalogButtonHandler catalogButtonHandler;

    @PostConstruct
    private void init(){
        callBackHandlers = new HashMap<>() {{
           put("PatisserieType", patisserieTypeHandler);
           put("CartHandler", cartHandler);
           put("ConnectOperator", connectOperatorHandler);
           put("PatisserieSubType", patisserieSubTypeHandler);
           put("PatisserieFlavor", patisserieFlavorHandler);
           put("DeliveryMethod", deliveryMethodHandler);
           put("PatisserieSize", patisserieSizeHandler);
           put("CatalogHandler", catalogButtonHandler);
        }};
    }

    public List<Object> handle(Update update) {
        String data = update.getCallbackQuery().getData();
        String handlerName = callBackHandlers.keySet().stream().filter(data::contains).findFirst().orElse("Default Message Handler");
        return callBackHandlers.get(handlerName).handle(update);
    }

    public CallbackHandler(PatisserieTypeHandler patisserieTypeHandler,
                           CartHandler cartHandler,
                           ConnectOperatorHandler connectOperatorHandler,
                           PatisserieSubTypeHandler patisserieSubTypeHandler,
                           PatisserieFlavorHandler patisserieFlavorHandler,
                           DeliveryMethodHandler deliveryMethodHandler,
                           PatisserieSizeHandler patisserieSizeHandler,
                           CatalogButtonHandler catalogButtonHandler) {
        this.patisserieTypeHandler = patisserieTypeHandler;
        this.cartHandler = cartHandler;
        this.connectOperatorHandler = connectOperatorHandler;
        this.patisserieSubTypeHandler = patisserieSubTypeHandler;
        this.patisserieFlavorHandler = patisserieFlavorHandler;
        this.deliveryMethodHandler = deliveryMethodHandler;
        this.patisserieSizeHandler = patisserieSizeHandler;
        this.catalogButtonHandler = catalogButtonHandler;
    }
}
