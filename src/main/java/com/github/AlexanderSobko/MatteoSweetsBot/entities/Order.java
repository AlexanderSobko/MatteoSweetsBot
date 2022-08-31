package com.github.AlexanderSobko.MatteoSweetsBot.entities;

import com.github.AlexanderSobko.MatteoSweetsBot.enums.OrderStatus;
import com.github.AlexanderSobko.MatteoSweetsBot.enums.PatisserieType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@Entity(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private LocalDateTime date;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "order", cascade = CascadeType.REMOVE)
    private List<Patisserie> patisseries = new ArrayList<>();

    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

    private Integer totalPrice;

    @Override
    public String toString() {
        String chocolates = patisseries.stream()
                .filter(p -> p.getPatisserieType().equals(PatisserieType.CHOCOLATE))
                .map(Patisserie::toString)
                .collect(Collectors.joining());
        String cakes = patisseries.stream()
                .filter(p -> p.getPatisserieType().equals(PatisserieType.CAKE))
                .map(Patisserie::toString)
                .collect(Collectors.joining());
        String chocolateList = chocolates.isEmpty() ? "" : "Шоколад:\n" + chocolates;
        String cakeList = cakes.isEmpty() ? "" : "Торты:\n" + cakes;
        return chocolateList + cakeList;
    }

}
