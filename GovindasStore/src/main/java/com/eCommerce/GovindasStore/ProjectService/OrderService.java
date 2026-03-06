package com.eCommerce.GovindasStore.ProjectService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eCommerce.GovindasStore.Model.CartItem;
import com.eCommerce.GovindasStore.Model.Order;
import com.eCommerce.GovindasStore.Model.OrderItem;
import com.eCommerce.GovindasStore.Model.Product;
import com.eCommerce.GovindasStore.ProductRepository.OrderRepository;
import com.eCommerce.GovindasStore.ProductRepository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderService {
	@Autowired
    private CartService cartService;
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    
    @Transactional
    public Order placeOrder(String email) {
		
    	List<CartItem> cartItems=cartService.getCartItems(email);
 
 
    	
    	if (cartItems.isEmpty()) {
            throw new RuntimeException("Cannot place order: Cart is empty");
        }
    	
    	
    	
    	//Check if there is enough stock 
    	//and then "deduct" it after a successful purchase.
    	
    	for(CartItem item: cartItems) {
    		Product product =item.getProduct();
    		
    		
    		
    		if(product.getStockQuantity()<item.getQuantity()) {
    			throw new RuntimeException("Low stock for: " + product.getName());
    		}
    		
    		// Deduct stock
    		
    		product.setStockQuantity(product.getStockQuantity()-item.getQuantity());
    		productRepository.save(product);
    	}
    	
    	
    	// 1. Calculate Total and Create Order Items
    	
    	double total=0;
    	List<OrderItem> orderItems=new ArrayList<>();
    	
    	
    	for( CartItem cartitem : cartItems) {
    		OrderItem oi=new OrderItem();
    		oi.setProductName(cartitem.getProduct().getName());
    		oi.setPriceAtPurchase(cartitem.getProduct().getPrice());
    		oi.setQuantity(cartitem.getQuantity());
    		
    		orderItems.add(oi);
    		
    		total+=(cartitem.getProduct().getPrice()*cartitem.getQuantity());
    	}
    	
    	// 2. Create the Order
    	
    	Order order =new Order();
    	order.setItems(orderItems);
    	//order.setOrderDate(LocalDateTime.now());
    	order.setTotalAmount(total);
    	order.setUserEmail(email);
    	
    	
    	// 3. Save Order and Clear Cart
    	
    	//Order savedOrder=orderRepository.save(order);
    	cartService.clearCart(email);
    	
		/*
		 * Order finalorder = new Order(); finalorder.setUserEmail(email);
		 * finalorder.setStatus("Placed");
		 */
    	
    	order.setStatus("Placed");
    	return  orderRepository.save(order);
    	
    }


	public List<Order> getOrdersByUser(String email) {
		
		return orderRepository.findByUserEmail(email);
	}
	
	
}
