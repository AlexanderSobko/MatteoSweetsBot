package com.github.AlexanderSobko.MatteoSweetsBot.services;

import com.github.AlexanderSobko.MatteoSweetsBot.entities.User;
import com.github.AlexanderSobko.MatteoSweetsBot.entities.Order;
import com.github.AlexanderSobko.MatteoSweetsBot.enums.OrderStatus;
import com.github.AlexanderSobko.MatteoSweetsBot.exceptions.ResourceNotFoundException;
import com.github.AlexanderSobko.MatteoSweetsBot.models.dtos.OrderDTO;
import com.github.AlexanderSobko.MatteoSweetsBot.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class OrderService {

    private final UserService UserService;
    private final OrderRepository orderRepository;

    public List<Order> findAllByUserId(String userId) {
        User User = UserService.getUserByTelegramId(userId);
        return orderRepository.findAllByUserId(User.getId());
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public void clearLastOrder(String chatId) {
        Order order = getLastOrder(chatId);
        orderRepository.delete(order);
    }

    @Autowired
    public OrderService(UserService UserService, OrderRepository orderRepository) {
        this.UserService = UserService;
        this.orderRepository = orderRepository;
    }

    public Order getLastOrder(String userId) {
        User User = UserService.getUserByTelegramId(userId);
        Order order = orderRepository.findByUserIdAndOrderStatus(User.getId(), null);
        if (order == null){
            order = new Order();
            order.setUser(UserService.getUserByTelegramId(userId));
            orderRepository.save(order);
        }
        return order;
    }

    public String finishOrder(String userId) {
        Order order = getLastOrder(userId);
        order.setOrderStatus(OrderStatus.PENDING_PAYMENT);
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

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    public OrderDTO getOrderDTO(Long id) {
        return orderToOrderDTO(orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found " + id)));
    }

    private OrderDTO orderToOrderDTO(Order order) {
        return new OrderDTO(
                order.getId(),
                order.getUser().getFirstName(),
                order.getUser().getLastName(),
                order.getDate(),
                order.toString(),
                order.getPatisseries(),
                order.getOrderStatus(),
                order.getTotalPrice(),
                order.getUser().getDeliveryMethod(),
                order.getUser().getDeliveryAddress());
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
