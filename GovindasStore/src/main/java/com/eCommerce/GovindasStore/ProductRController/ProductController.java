package com.eCommerce.GovindasStore.ProductRController;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eCommerce.GovindasStore.Model.Product;
import com.eCommerce.GovindasStore.ProductRepository.ProductRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/products")
public class ProductController {
	@Autowired
    private ProductRepository productRepository;
	
	// 1. View all products for the Store
	@GetMapping("/store")
    public List<Product> viewStore() {
		log.info("API to fetch all products : ");
        return productRepository.findAll();
    }
    
    @PostMapping("/admin/add-product") // Use POST for saving data
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
    	if (product.getStockQuantity() < 0) {
            return ResponseEntity.badRequest().build();
        }
    	Product savedProduct=productRepository.save(product);
        return ResponseEntity.ok(savedProduct);
    }
    
    @GetMapping("/low-stock")
    public List<Product> getLowStockProducts() {
        // Returns products with less than 5 items left
        return productRepository.findByStockQuantityLessThan(5);
    }
    
}
