package com.github.AlexanderSobko.MatteoSweetsBot.models.entities;

import com.github.AlexanderSobko.MatteoSweetsBot.enums.PatisserieSubType;
import com.github.AlexanderSobko.MatteoSweetsBot.enums.PatisserieType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity(name = "products")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String name;
    String shortDesc;
    String description;
    short price;
    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    byte[] photo;
    @Column
    @Enumerated(value = EnumType.STRING)
    PatisserieType type;
    @Column
    @Enumerated(value = EnumType.STRING)
    PatisserieSubType subType;
}
