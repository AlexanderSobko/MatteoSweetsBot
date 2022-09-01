package com.github.AlexanderSobko.MatteoSweetsBot.controllers;

import com.github.AlexanderSobko.MatteoSweetsBot.models.entities.User;
import com.github.AlexanderSobko.MatteoSweetsBot.exceptions.ResourceNotFoundException;
import com.github.AlexanderSobko.MatteoSweetsBot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getCustomers() {
        return new ResponseEntity<>(userService.getMSUsers(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getCustomer(@PathVariable(value = "id") Long id) {
        User user = userService.getUserById(id).orElseThrow(
                () -> new ResourceNotFoundException("User not found")
        );
        return ResponseEntity.ok().body(user);
    }

    @PutMapping("{id}")
    public User updateCustomer(@Valid @RequestBody User newData, @PathVariable(value = "id") Long id){
        System.out.println(newData);
//        ------------------------------------------------------------------------------------------------------
        return newData;
    }

    @PostMapping
    public User saveCustomer(@Valid @RequestBody User user) {
        return userService.save(user);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> removeCustomer(@PathVariable(value = "id") Long id) {
        User user = userService.getUserById(id).orElseThrow(
                () -> new ResourceNotFoundException("User not found " + id)
        );
        userService.deleteUser(user);
        return ResponseEntity.ok().build();
    }

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
}
