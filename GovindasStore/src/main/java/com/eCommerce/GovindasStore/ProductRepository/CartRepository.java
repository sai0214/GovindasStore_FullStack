package com.eCommerce.GovindasStore.ProductRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.eCommerce.GovindasStore.Model.CartItem;
import java.util.List;

public interface CartRepository extends JpaRepository<CartItem, Long>{
	List<CartItem> findByUserEmail(String userEmail);
	void deleteByUserEmail(String email);
	
}
