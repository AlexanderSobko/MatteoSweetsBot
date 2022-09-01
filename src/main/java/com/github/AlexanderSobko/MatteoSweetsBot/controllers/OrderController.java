package com.github.AlexanderSobko.MatteoSweetsBot.controllers;

import com.github.AlexanderSobko.MatteoSweetsBot.models.entities.Order;
import com.github.AlexanderSobko.MatteoSweetsBot.models.dtos.OrderDTO;
import com.github.AlexanderSobko.MatteoSweetsBot.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/recent")
    public ResponseEntity<?> getAllOrders() {
        return new ResponseEntity<>(orderService.getOrders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok().body(orderService.getOrderDTO(id));
    }

    @PutMapping("/{id}")
    public Order updateOrder(@RequestBody Order newOrder, @PathVariable(value = "id") Long id){
        System.out.println(newOrder);
        return newOrder;
//        return userService.getCustomer(id)
//                .map(customer -> {
//                    customer.setFirstName(newData.getFirstName());
//                    customer.setLastName(newData.getLastName());
//                    customer.setDeliveryMethod(newData.getDeliveryMethod());
//                    customer.setDeliveryAddress(newData.getDeliveryAddress());
//                    customer.setPhoneNumber(newData.getPhoneNumber());
//                    customer.setPhoto(newData.getPhoto());
//                    return userService.save(customer);
//                })
//                .orElseGet(()->{
//                    newData.setId(id);
//                    return userService.save(newData);
//                });
    }

    @PostMapping
    public Order saveCustomer(@RequestBody Order order) {
        System.out.println(order);
        return orderService.save(order);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> removeCustomer(@PathVariable(value = "id") Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok().build();
    }

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
}
