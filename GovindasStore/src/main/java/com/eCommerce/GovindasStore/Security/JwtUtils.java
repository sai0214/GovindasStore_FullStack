package com.eCommerce.GovindasStore.Security;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtils {
	
	private String jwtSecret="GovindasStoreVyashasPureVegOnlyindusAllowedJaiHindJaiIndia11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111";
	private int jwtExparation =86400000;
	
	public String generateTocken(String email) {
		return Jwts.builder()
				.setSubject(email)
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime()+jwtExparation))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}
	
	
	
	
	@SuppressWarnings("deprecation")
	public String getEmailIdFromToken(String token) {		
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();

	}
	
	@SuppressWarnings("deprecation")
	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
			return true;
		}
		catch(Exception e){
			return false;
		}
	}
}
