package com.github.AlexanderSobko.MatteoSweetsBot.enums;

public enum BiscuitFlavor {

    MILK_SUNDAE("\"Молочный пломбир с карамелизированным бананом\""),
    CLASSIC_VANILLA("\"Классический ванильный\""),
    CHOCOLATE_TROPICS("\"Шоколадные тропики\""),
    RAFFAELLO("\"Рафаэлло\""),
    RED_VELVET("\"Красный бархат с вишней\""),
    RASPBERRY_BUBBLE("\"Малиновый бабл\""),
    SNICKERS("\"Сникерс\""),
    COFFEE_PEAR("\"Кофейная груша\""),
    CRISPY_CHERRY("\"Хрустящая вишня\"");

    String rus;

    BiscuitFlavor(String rus) {
        this.rus = rus;
    }
}
