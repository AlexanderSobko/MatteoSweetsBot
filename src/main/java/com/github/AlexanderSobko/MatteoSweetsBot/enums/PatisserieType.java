package com.github.AlexanderSobko.MatteoSweetsBot.enums;

import java.util.List;

import static com.github.AlexanderSobko.MatteoSweetsBot.enums.PatisserieSubType.*;


public enum PatisserieType {

    CAKE(List.of(MOUSSE_CAKE,BISCUIT_CAKE)),
    CHOCOLATE(List.of(WHITE_CHOCOLATE, BLACK_CHOCOLATE, MILK_CHOCOLATE, CARAMEL_CHOCOLATE));

    List<PatisserieSubType> subTypes;

    PatisserieType(List<PatisserieSubType> subTypes) {
        this.subTypes = subTypes;
    }

    public List<PatisserieSubType> getSubTypes() {
        return this.subTypes;
    }
}
