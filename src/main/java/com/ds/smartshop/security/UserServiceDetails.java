package com.ds.smartshop.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ds.smartshop.entity.User;
import com.ds.smartshop.repository.IUserRepository;

@Service
public class UserServiceDetails implements UserDetailsService{

	
	private final IUserRepository userRepository;
	
	public UserServiceDetails(IUserRepository userRepository) {
		this.userRepository=userRepository;
	}

	
	@Transactional(readOnly=true)
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user=userRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("User not found"));
		
		
		
		
		return org.springframework.security.core.userdetails.User
				.withUsername(user.getEmail())
				.password(user.getPassword())
				.authorities(
						user.getRoles().stream()
						.map(role-> new SimpleGrantedAuthority(
								"ROLE_"+role.getName()))
						.toList())
				.disabled(!user.isEnabled())
				.build();
	}
	
	
}
