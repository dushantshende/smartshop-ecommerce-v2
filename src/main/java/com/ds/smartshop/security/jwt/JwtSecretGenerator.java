package com.ds.smartshop.security.jwt;

import java.security.SecureRandom;
import java.util.Base64;

public class JwtSecretGenerator {

	
	public static void main(String[] args) {
		
		byte[] key= new byte[32];
		
		new SecureRandom().nextBytes(key);
		
		String secret=Base64.getEncoder()
				.encodeToString(key);
		
		System.out.println("JWT Secret: "+secret);
	}
}
