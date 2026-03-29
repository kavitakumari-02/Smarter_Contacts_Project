package com.springboot.project1.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.project1.model.User;

public interface UserRepository extends JpaRepository<User,Integer>{

	public User findByEmail(String email);
		
	
}
