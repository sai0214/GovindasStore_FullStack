package com.eCommerce.GovindasStore.ProductRepository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eCommerce.GovindasStore.Model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

	List<Product> findByStockQuantityLessThan(int i);

}
