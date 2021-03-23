package com.spgf.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spgf.blog.model.User;

public interface UserRepository extends  JpaRepository<User, Long>{
	
	public User findByUsername(String username);
	
	public User findByEmail(String email);
	
	@Query("SELECT user FROM User user WHERE user.id=:idUser")
	public User findUserById(@Param("idUser") Long id);
	
	public List<User> findByUsernameContaining(String username);
}
