package com.ds.smartshop.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ds.smartshop.dto.UserRegistrationRequest;
import com.ds.smartshop.entity.Role;
import com.ds.smartshop.entity.User;
import com.ds.smartshop.exception.DuplicateResourceException;
import com.ds.smartshop.repository.IRoleRepository;
import com.ds.smartshop.repository.IUserRepository;




@Service
public class UserServiceImpl implements IUserService {

	private final IUserRepository userRepository;
	private final IRoleRepository roleRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	public UserServiceImpl(IUserRepository userRepository, IRoleRepository roleRepository, PasswordEncoder passwordEncoder) {
		this.roleRepository=roleRepository;
		this.userRepository=userRepository;
		this.passwordEncoder=passwordEncoder;
	}
	
	@Transactional
	@Override
	public void registerUser(UserRegistrationRequest request) {
		
		if(userRepository.findByEmail(request.getEmail()).isPresent()) {
			throw new DuplicateResourceException ("Email already exists");
		}
		if (userRepository.findByPhone(request.getPhone()).isPresent()) {
			throw new DuplicateResourceException("Phone number already exists");
		}
		
		Role customerRole=roleRepository.findByName("CUSTOMER").orElseThrow(()-> new RuntimeException("CUSTOMER role not found"));
		
		User user=new User();
		user.setFullName(request.getFullName());
		user.setEmail(request.getEmail());
		user.setPhone(request.getPhone());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.getRoles().add(customerRole);
		user.setEnabled(true);
		userRepository.save(user);
	}

}
