package com.github.AlexanderSobko.MatteoSweetsBot.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.AlexanderSobko.MatteoSweetsBot.enums.PatisserieSubType;
import com.github.AlexanderSobko.MatteoSweetsBot.enums.PatisserieType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static com.github.AlexanderSobko.MatteoSweetsBot.enums.PatisserieSubType.*;
import static com.github.AlexanderSobko.MatteoSweetsBot.enums.PatisserieSubType.MOUSSE_CAKE;

@Data
@Entity
@NoArgsConstructor
public class Patisserie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Enumerated(value = EnumType.STRING)
    private PatisserieType patisserieType;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column
    @Enumerated(value = EnumType.STRING)
    private PatisserieSubType patisserieSubType;

    private String size;

    private String flavor;

    private Integer price;

    @Override
    public String toString() {
        if (patisserieType.equals(PatisserieType.CAKE)) {
            String[] mousse = new String[]{"\"Медовый апельсин\"", "\"Ананасовый сорбет\"", "\"Дабл эпл\"", "\"Три шоколада\"", "\"Клубничный Шейк\"", "\"Лимонный блюз\"", "\"Бейлис\""};
            String[] biscuit = new String[]{"\"Молочный пломбир с карамелизированным бананом\"", "\"Классический ванильный\"", "\"Шоколадные тропики\"", "\"Рафаэлло\"", "\"Красный бархат с вишней\"", "\"Малиновый бабл\"", "\"Сникерс\"", "\"Кофейная груша\"", "\"Хрустящая вишня\"",};
            String f;
            String t;
            if (patisserieSubType.equals(MOUSSE_CAKE)) {
                f = mousse[Integer.parseInt(flavor) - 1];
                t = "Муссовый";
            } else {
                f = biscuit[Integer.parseInt(flavor) - 1];
                t = "Бисквитный";
            }
            return String.format("– %s торт %s.\n", t, f);
        } else {
            String s = size.equals("Big") ? "Большая" : "Маленькая";
            String t = "";
            if (patisserieSubType.equals(BLACK_CHOCOLATE))
                t = "черного";
            else if (patisserieSubType.equals(CARAMEL_CHOCOLATE))
                t = "карамелизированного";
            else if (patisserieSubType.equals(MILK_CHOCOLATE))
                t = "молочного";
            else if (patisserieSubType.equals(WHITE_CHOCOLATE))
                t = "белого";
            return String.format("– %s плитка %s шоколада.\n", s, t);
        }
    }
}
