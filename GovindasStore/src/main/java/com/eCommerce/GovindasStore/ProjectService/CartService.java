package com.eCommerce.GovindasStore.ProjectService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eCommerce.GovindasStore.Model.CartItem;
import com.eCommerce.GovindasStore.Model.Product;
import com.eCommerce.GovindasStore.ProductRepository.CartRepository;
import com.eCommerce.GovindasStore.ProductRepository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class CartService {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private ProductRepository productRepositiry;

	public CartItem addToCart(CartItem newItem) {
		
		// fetch product first weather it is available or not in produts 
		
		Product freashProduct=productRepositiry.findById(newItem.getProduct().getId()).orElseThrow(()-> new RuntimeException("Product Not Found"));
		
		if (freashProduct.getStockQuantity() <= 0) {
	        throw new RuntimeException("Sorry, this item just went out of stock!");
	    }
		

		if (newItem.getQuantity() > freashProduct.getStockQuantity()) {
	        throw new RuntimeException("Only " + freashProduct.getStockQuantity() + " units available in stock.");
	    }
		
		List<CartItem> existingCart = cartRepository.findByUserEmail(newItem.getUserEmail());

		for (CartItem item : existingCart) {
			if (item.getProduct().getId().equals(freashProduct.getId())) {
				
				int total_cart_quantity=item.getQuantity()+newItem.getQuantity();
				
				if(freashProduct.getStockQuantity()<total_cart_quantity) {
					throw new RuntimeException("Cannot add more. Real-time stock is only " + freashProduct.getStockQuantity());
				}
				
				// Update existing item
				//item.setQuantity(item.getQuantity() + newItem.getQuantity());
				item.setQuantity(total_cart_quantity);
				
				
				item.setProduct(freashProduct); 
				return cartRepository.save(item);
			}
		}

		// 2. Add as a new item if not already in cart
		newItem.setProduct(freashProduct);
		return cartRepository.save(newItem);
	}

	public List<CartItem> getCartItems(String mail) {
		return cartRepository.findByUserEmail(mail);
	}

	@Transactional
	public void clearCart(String email) {
		cartRepository.deleteByUserEmail(email);
	}
	
	
	@Transactional
	public String removeItem(Long cartItemId) {
		CartItem cartItem=cartRepository.findById(cartItemId).orElseThrow(()-> new RuntimeException("Item Not Found In Cart"));
		
		if(cartItem.getQuantity()>1) {
			cartItem.setQuantity(cartItem.getQuantity()-1);
			cartRepository.save(cartItem);
			return "Quantity Decreased BY 1 : "+ cartItem.getQuantity() +" is remaing Quantity";
		}
		else {
			cartRepository.delete(cartItem);
			return "Prodcut Deleted from cart";
		}
	}
}
