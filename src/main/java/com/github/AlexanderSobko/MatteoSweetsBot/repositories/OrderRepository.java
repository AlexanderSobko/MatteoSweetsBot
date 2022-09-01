package com.github.AlexanderSobko.MatteoSweetsBot.repositories;

import com.github.AlexanderSobko.MatteoSweetsBot.models.entities.Order;
import com.github.AlexanderSobko.MatteoSweetsBot.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByUserId(Long userId);

    Order findByUserIdAndOrderStatus(Long userId, OrderStatus orderStatus);
}
