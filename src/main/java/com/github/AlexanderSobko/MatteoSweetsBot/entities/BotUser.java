package com.github.AlexanderSobko.MatteoSweetsBot.entities;

import com.github.AlexanderSobko.MatteoSweetsBot.models.UserStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.awt.*;
import java.io.File;

@Data
@NoArgsConstructor
@Entity(name = "customers")
public class BotUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String telegramId;

    private String phoneNumber;

    private String firstName;

    private String lastName;

    private String userName;

    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    private byte[] photo;

    private String deliveryMethod = "\"Самовывоз\"";

    private String deliveryAddress = "Адресс: г. Краснодар, ул. 40 лет Победы 33/6";

    @Override
    public String toString() {
        return """
                Customer
                id = %s
                Name = %s %s
                UserName = @%s
                DeliveryMethod = %s
                deliveryAddress = %s
                """.formatted(telegramId,firstName,lastName, userName, deliveryMethod,deliveryAddress);
    }
}
