package com.spgf.blog.service;

import java.util.List;

import com.spgf.blog.model.Role;
import com.spgf.blog.model.User;

public interface AccountService {

	public void saveUser(User user);

	public User findByUsername(String username);

	public User findByEmail(String email);

	public List<User> userList();

	public Role findUserRoleByName(String role);

	public Role saveRole(Role role);

	public void updateUser(User user);

	public User findUserById(Long id);
	
	public void deleteUser(User user);
	
	public void resetPassword(User user);
	
	public List<User> getUserListByUsername(String username);
	
	public void simpleSave(User user);
}
