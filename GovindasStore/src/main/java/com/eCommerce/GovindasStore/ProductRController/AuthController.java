package com.eCommerce.GovindasStore.ProductRController;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eCommerce.GovindasStore.Model.User;
import com.eCommerce.GovindasStore.ProductRepository.UserRepository;
import com.eCommerce.GovindasStore.Security.JwtUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private JwtUtils jwtUtils;
	@Autowired 
	private BCryptPasswordEncoder encoder;
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping("/register")
	public String signup(@RequestBody User user) {
		log.info("The signUp is called by user : "+ user.getEmail());
		user.setPassword(encoder.encode(user.getPassword()));
		userRepository.save(user);
		
		return "User "+ user.getEmail()+ " Saved Successfully";	
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User user) {
		
		log.info("The login is called by user : "+ user.getEmail());
		User dbUser =userRepository.findByEmail(user.getEmail());
		log.info("The login is called by user : "+dbUser.getEmail());
		
		String token1="";
		if(dbUser!=null && encoder.matches(user.getPassword(),dbUser.getPassword())) {
			token1 = jwtUtils.generateTocken(dbUser.getEmail());
			Map<String,String> response=new HashMap<>();
			
			response.put("token", token1);
			response.put("email", dbUser.getEmail());
			response.put("role",dbUser.getRole());
			
			log.info("The login is called by user : "+dbUser.getEmail() );
			log.info("The login is called by user : "+dbUser.getRole());
			
			return ResponseEntity.ok(response);
		}
		
		throw new RuntimeException("Invalid Credentials");
	}
}
