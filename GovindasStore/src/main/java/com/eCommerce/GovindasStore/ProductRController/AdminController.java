package com.eCommerce.GovindasStore.ProductRController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eCommerce.GovindasStore.Model.User;
import com.eCommerce.GovindasStore.ProductRepository.UserRepository;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/users")
	public List<User> getAllUsers(){
		log.info("To fetch all the users");
		return userRepository.findAll();
	}
	
	@DeleteMapping("/user/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable Long id ){
		userRepository.deleteById(id);
		return ResponseEntity.ok("User Deleted Successfully");
		
	}
}
