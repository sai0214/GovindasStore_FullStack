package com.eCommerce.GovindasStore.config;
	
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.eCommerce.GovindasStore.Security.JwtFilter;
	
@Configuration
@EnableWebSecurity
public class SecurityConfig {
		
	@Autowired
	private JwtFilter jwtFilter;
		
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{	
		http
			.csrf(csrf-> csrf.disable()) // To disable csrf 
			.cors(Customizer.withDefaults()) //to use post request from server we need to use this
			.authorizeHttpRequests(auth->auth
				.requestMatchers("/api/products/store").permitAll()   	// Any one can see products
				.requestMatchers("/api/auth/**").permitAll()
				//.requestMatchers("/api/admin/**","/api/products/add").hasAuthority("ROLE_ADMIN")
				.requestMatchers("/api/admin/**","/api/products/add").authenticated()
				.anyRequest().authenticated())    					// cart/orders need Login 
			
				.sessionManagement(session-> session
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // No cookies, use JWT
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
			.oauth2Login(Customizer.withDefaults());
		return http.build();
	}
		
		
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();  //Encrypts password in DB 
	}
		
	@Bean
	public WebMvcConfigurer corConfigurer() {
		return new WebMvcConfigurer() {
			
		public void addCorsMappings(CorsRegistry registry) {
			registry.addMapping("/**")
					.allowedOrigins("http://127.0.0.1:5500", "null")
					.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
					.allowedHeaders("*");
			}
		};
	}
	
	@Bean
	ClientRegistrationRepository clientRegistrationRepository() {
		ClientRegistration github= githubClientRegistration();
		//ClientRegistration google=googleClientRegistration();
		return new InMemoryClientRegistrationRepository(github);
	}
	
	private ClientRegistration githubClientRegistration() {
		return CommonOAuth2Provider.GITHUB.getBuilder("github")
			.clientId("Ov23likH7IuZhkmjf8Ih")
			.clientSecret("b058d50526b4b863b6b0b8363de4c5ef85fd9f45").build();
		
	}
	
	/*
	 * private ClientRegistration googleClientRegistration() { return
	 * CommonOAuth2Provider.GOOGLE.getBuilder("google") .clientId("")
	 * .clientSecret("").build();
	 * 
	 * }
	 */
}
