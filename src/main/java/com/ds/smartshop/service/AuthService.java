package com.ds.smartshop.service;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ds.smartshop.entity.User;
import com.ds.smartshop.repository.IUserRepository;
import com.ds.smartshop.security.jwt.JwtService;

@Service
public class AuthService {

	private final IUserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	
	
	public AuthService(IUserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
	}
	
	//user login
	public String login(String email,String password) {
		
		
		//find user by email
		User user=userRepository.findByEmail(email).orElseThrow(()-> new BadCredentialsException("Invalid email or password"));
		
		//verify password
		if (!passwordEncoder.matches(password, user.getPassword())) {
			throw new BadCredentialsException("Invalid email or password");
		}
		
		//check account status
		
		if (!user.isEnabled()) {
			throw new DisabledException ("User account is disabled ");
		}
		
		//Generate JWT token
		
		return jwtService.generateToken(user.getEmail());
	}
	
}
