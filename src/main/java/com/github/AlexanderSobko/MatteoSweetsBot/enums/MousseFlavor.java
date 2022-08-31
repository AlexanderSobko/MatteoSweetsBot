package com.github.AlexanderSobko.MatteoSweetsBot.enums;

public enum MousseFlavor {

    HONEY_ORANGE("\"Медовый апельсин\""),
    PINEAPPLE_SORBET("\"Ананасовый сорбет\""),
    DOUBLE_APPLE("\"Дабл эпл\""),
    THREE_CHOCOLATES("\"Три шоколада\""),
    STRAWBERRY_SHAKE("\"Клубничный Шейк\""),
    LEMON_BLUES("\"Лимонный блюз\""),
    BAILEYS("\"Бейлис\"");

    String rus;

    MousseFlavor(String rus) {
        this.rus = rus;
    }
}
