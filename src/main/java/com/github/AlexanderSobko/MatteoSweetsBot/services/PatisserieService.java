package com.github.AlexanderSobko.MatteoSweetsBot.services;

import com.github.AlexanderSobko.MatteoSweetsBot.entities.Order;
import com.github.AlexanderSobko.MatteoSweetsBot.entities.Patisserie;
import com.github.AlexanderSobko.MatteoSweetsBot.models.PatisserieSubType;
import com.github.AlexanderSobko.MatteoSweetsBot.models.PatisserieType;
import com.github.AlexanderSobko.MatteoSweetsBot.repositories.PatisserieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PatisserieService {

    private final OrderService orderService;
    private final PatisserieRepository patisserieRepo;

    public String addPatisserieToOrder(String[] data, String chatId) {
        Order order = orderService.getLastOrder(chatId);
        Patisserie patisserie = parsePatisserie(data);
        patisserie.setOrder(order);
        patisserieRepo.save(patisserie);
        return patisserie.toString();
    }

    public Patisserie parsePatisserie(String[] data){
        Patisserie patisserie = new Patisserie();
        patisserie.setPatisserieType(PatisserieType.valueOf(data[1]));
        patisserie.setPatisserieSubType(PatisserieSubType.valueOf(data[2]));
        if (data[1].contains(PatisserieType.CAKE.name()))
            patisserie.setFlavor(data[3]);
        else
            patisserie.setSize(data[3]);
        return patisserie;
    }

    @Autowired
    public PatisserieService(OrderService orderService, PatisserieRepository patisserieRepo) {
        this.orderService = orderService;
        this.patisserieRepo = patisserieRepo;
    }
}
