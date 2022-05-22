package com.github.AlexanderSobko.MatteoSweetsBot.repositories;

import com.github.AlexanderSobko.MatteoSweetsBot.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByUserId(String userId);

    Order findByUserIdAndFinished(String userId, boolean finished);
}
