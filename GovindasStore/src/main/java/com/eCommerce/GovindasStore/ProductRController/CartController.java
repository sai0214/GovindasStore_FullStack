package com.eCommerce.GovindasStore.ProductRController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eCommerce.GovindasStore.Model.CartItem;
import com.eCommerce.GovindasStore.Model.Product;
import com.eCommerce.GovindasStore.ProductRepository.CartRepository;
import com.eCommerce.GovindasStore.ProductRepository.ProductRepository;
import com.eCommerce.GovindasStore.ProjectService.CartService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/cart")
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private CartRepository cardRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@PostMapping("/add/{productId}")
	public ResponseEntity<?> addToCart(@PathVariable Long productId) {
		log.info("This is an INFO log for add/productId called");
		
		String email=SecurityContextHolder.getContext().getAuthentication().getName();
		CartItem cartItem =new CartItem();
		
		Product product = productRepository.findById(productId).orElseThrow(()-> new RuntimeException());
		
		
		if (product.getStockQuantity() <= 0) {
	        // This sends the 400 error that the frontend "else if" catches
	        return ResponseEntity.badRequest().body("This product is sold out!");
	    }
		
		cartItem.setProduct(product);
		cartItem.setUserEmail(email);
		cartItem.setQuantity(1);
		cartService.addToCart(cartItem);
		return ResponseEntity.ok("Added");
	}
	
	@GetMapping("/view")
	public List<CartItem> getCart(){
		log.info("This is an INFO log message view called");
		String mail=SecurityContextHolder.getContext().getAuthentication().getName();
		return cartService.getCartItems(mail);
	}
	
	
	@DeleteMapping("/remove/{cartItemId}")
	public ResponseEntity<?> removeFromCart(@PathVariable Long cartItemId) {
	    cartService.removeItem(cartItemId);
	    return ResponseEntity.ok().body("Item removed from Govindas Store cart");
	}
	
	
	
	
	@DeleteMapping("/clear")
	public ResponseEntity<?> clearCart() {
	    String email = SecurityContextHolder.getContext().getAuthentication().getName();
	    cartService.clearCart(email);
	    return ResponseEntity.ok("Cart cleared successfully");
	}
	
}
