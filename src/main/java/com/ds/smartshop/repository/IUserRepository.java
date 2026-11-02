package com.ds.smartshop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ds.smartshop.entity.User;

public interface IUserRepository extends JpaRepository<User, Long> {

	public Optional<User>findByEmail(String email);
	
	public Optional<User>findByPhone(String phone);
	
	
}
