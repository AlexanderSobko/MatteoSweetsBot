package com.github.AlexanderSobko.MatteoSweetsBot.controllers;

import com.github.AlexanderSobko.MatteoSweetsBot.models.entities.Order;
import com.github.AlexanderSobko.MatteoSweetsBot.models.entities.Product;
import com.github.AlexanderSobko.MatteoSweetsBot.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        return new ResponseEntity<>(productService.getProducts(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok().body(productService.getProduct(id));
    }

    @PutMapping("/{id}")
    public Order updateProduct(@RequestBody Order newOrder, @PathVariable(value = "id") Long id){
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
    public Product saveProduct(@RequestBody Product product) {
        return productService.save(product);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> removeProduct(@PathVariable(value = "id") Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
}
