package com.ds.smartshop.security.jwt;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	@Value("${app.jwt.secret}")
	private String jwtSecret;
	
	@Value("${app.jwt.expiration}")
	private long jwtExpiration;

	
	//Generate JWT token	
	public String generateToken(String email) {
		
		return Jwts.builder()
				.subject(email)
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + jwtExpiration))
				.signWith(getSignInKey())
				.compact();
		
	}
	
	//check token expiration
	private Claims extractAllClaims(String token) {
		return Jwts.parser()
				.verifyWith(getSignInKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
	
	//Generate signing key
	private SecretKey getSignInKey() {
		
		byte[] keyBytes=Decoders.BASE64.decode(jwtSecret);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	//extract email from jwt token
	public String extractEmail(String token) {
		return extractAllClaims(token).getSubject();
	}
	
	//validate JWT token
	public boolean isTokenValid(String token , String email) {
		String extractedEmail= extractEmail(token);
		
		return extractedEmail.equals(email)
				&& !isTokenExpired(token);
	}
	
	//check token expiration
	private boolean isTokenExpired(String token) {
		return extractAllClaims(token)
				.getExpiration()
				.before(new Date());
	}
}
