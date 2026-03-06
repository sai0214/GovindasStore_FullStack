package com.eCommerce.GovindasStore.ProductRController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eCommerce.GovindasStore.Model.Order;
import com.eCommerce.GovindasStore.ProductRepository.OrderRepository;
import com.eCommerce.GovindasStore.ProjectService.OrderService;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@PostMapping("/place")
	public ResponseEntity<String> placeOrder() {
		String email=SecurityContextHolder.getContext().getAuthentication().getName();
		
		System.out.println("Email = " + email);
		orderService.placeOrder(email);
		return ResponseEntity.ok("Order placed"+email);
	}
	
	
	@GetMapping("/history")
	public List<Order> getMyOrders(){
		String email=SecurityContextHolder.getContext().getAuthentication().getName();
		
		return orderService.getOrdersByUser(email);
	}
	
	@PutMapping("/update-Status")
	public Order updateOrderStatus(@RequestParam Long OrderId, @RequestParam String status) {
		Order order = orderRepository.findById(OrderId).orElseThrow(() ->new RuntimeException("Order Not Found"));
		
		order.setStatus("PLACED");
		return orderRepository.save(order);
	}
}
