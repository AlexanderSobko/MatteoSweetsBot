package com.github.AlexanderSobko.MatteoSweetsBot.models.dtos;

import com.github.AlexanderSobko.MatteoSweetsBot.models.entities.Patisserie;
import com.github.AlexanderSobko.MatteoSweetsBot.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;


@Data
@Component
@AllArgsConstructor
@Scope(scopeName = "prototype")
public class OrderDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private LocalDateTime date;

    private String description;

    private List<Patisserie> patisseries;

    private OrderStatus orderStatus;

    private Integer totalPrice;

    private String deliveryMethod;

    private String deliveryAddress;

    public OrderDTO(Long id, String firstName, String lastName, LocalDateTime date, String orderStatus, Integer totalPrice, String deliveryMethod, String deliveryAddress) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.orderStatus = OrderStatus.valueOf(orderStatus);
        this.totalPrice = totalPrice;
        this.deliveryMethod = deliveryMethod;
        this.deliveryAddress = deliveryAddress;
        this.date = date;
    }

    public OrderDTO() {
    }
}
