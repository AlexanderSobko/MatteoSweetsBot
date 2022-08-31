package com.github.AlexanderSobko.MatteoSweetsBot.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = "\\d+", message = "TelegramId can only be a number!")
    private String telegramId;

    @Pattern(regexp = "\\+7\\d{10}",
            message = "The phone number must start with \"+7\" and contain 11 digits!")
    private String phoneNumber;

    private String firstName;

    private String lastName;

    private String username;

    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    private byte[] photo;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user",cascade = CascadeType.REMOVE)
    private List<Order> orders = new ArrayList<>();

    private String deliveryMethod = "\"Самовывоз\"";

    private String deliveryAddress = "Адресс: г. Краснодар, ул. 40 лет Победы 33/6";

    @Override
    public String toString() {
        return """
                User
                Id = %s
                Name = %s %s
                Username = @%s
                DeliveryMethod = %s
                DeliveryAddress = %s
                """.formatted(id,firstName,lastName, username, deliveryMethod,deliveryAddress);
    }

}
