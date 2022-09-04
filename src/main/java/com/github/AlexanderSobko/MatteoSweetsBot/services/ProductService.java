package com.github.AlexanderSobko.MatteoSweetsBot.services;

import com.github.AlexanderSobko.MatteoSweetsBot.models.entities.Product;
import com.github.AlexanderSobko.MatteoSweetsBot.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepo;

    @Autowired
    public ProductService(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    public List<Product> getProducts() {
        return productRepo.findAll();
    }

    public Product getProduct(Long id) {
        return productRepo.findById(id).get();
    }

    public Product save(Product product) {
        return productRepo.save(product);
    }

    public void deleteProduct(Long id) {
        productRepo.deleteById(id);
    }
}
