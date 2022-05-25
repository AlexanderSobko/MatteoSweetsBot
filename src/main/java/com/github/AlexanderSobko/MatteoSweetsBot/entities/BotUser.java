package com.github.AlexanderSobko.MatteoSweetsBot.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@Entity(name = "users")
public class BotUser {

    @Id
    private String id;

    private String firstName;

    private String lastName;

    private String userName;

    private String deliveryMethod = "\"Самовывоз\"";

    private String deliveryAddress = "г. Краснодар, ул. 40 лет Победы 33/6";

    private boolean waiting;

    @Override
    public String toString() {
        return """
                Имя: %s %s.
                Контакт: %s.
                Способ доставки: %s.
                Адресс доставки: %s.
                """.formatted(lastName, firstName, userName, deliveryMethod, deliveryAddress);
    }
}
