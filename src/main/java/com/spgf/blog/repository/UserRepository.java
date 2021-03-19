package com.spgf.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spgf.blog.model.User;

public interface UserRepository extends  JpaRepository<User, Long>{
	
	public User findByUsername(String username);
	
	public User findByEmail(String email);
	
	public User findUserById(Long id);
	
	public List<User> findByUsernameContaining(String username);
}
