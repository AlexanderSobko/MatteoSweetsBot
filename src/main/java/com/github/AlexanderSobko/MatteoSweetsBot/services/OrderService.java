package com.github.AlexanderSobko.MatteoSweetsBot.services;

import com.github.AlexanderSobko.MatteoSweetsBot.entities.Order;
import com.github.AlexanderSobko.MatteoSweetsBot.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class OrderService {

    private final BotUserService botUserService;
    private final OrderRepository orderRepository;

    public List<Order> findAllByUserId(String userId) {
        return orderRepository.findAllByUserId(userId);
    }

    public void save(Order order) {
        orderRepository.save(order);
    }

    public void clearLastOrder(String chatId) {
        Order order = getLastOrder(chatId);
        orderRepository.delete(order);
    }

    @Autowired
    public OrderService(BotUserService botUserService, OrderRepository orderRepository) {
        this.botUserService = botUserService;
        this.orderRepository = orderRepository;
    }

    public Order getLastOrder(String userId) {
        Order order = orderRepository.findByUserIdAndFinished(userId, false);
        if (order == null){
            order = new Order(botUserService.getUserById(userId));
            orderRepository.save(order);
        }
        return order;
    }

    public String finishOrder(String userId) {
        Order order = getLastOrder(userId);
        order.setFinished(true);
        order.setDate(LocalDateTime.now());
        orderRepository.save(order);
        return getOrderInfo(order);
    }

    public boolean isLastOrderEmpty(String chatId) {
        Order order = getLastOrder(chatId);
        return order.getPatisseries().isEmpty();
    }

    public String getLastOrderInfo(String chatId) {
        return getLastOrder(chatId).toString();
    }

    public String getOrderInfo(Order order){
        String date = order.getDate().format(DateTimeFormatter.ofPattern("dd MMMM yyyy"));
        return """
                Заказ № %d.
                
                Содержание заказа:
                
                %s
                
                Дата: %s
                """.formatted(order.getId(), order.toString(), date);
    }
}
