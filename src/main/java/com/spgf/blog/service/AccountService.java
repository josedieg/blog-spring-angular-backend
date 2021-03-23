package com.spgf.blog.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.spgf.blog.model.Role;
import com.spgf.blog.model.User;

public interface AccountService {

	public User saveUser(String name, String username, String email);

	public User findByUsername(String username);

	public User findByEmail(String userEmail);

	public List<User> userList();

	public Role findUserRoleByName(String string);

	public Role saveRole(Role role);

	public void updateUserPassword(User User, String newpassword);
	
	public User updateUser(User user, HashMap<String, String> request);

	public User simpleSaveUser(User user);

	public User findUserById(Long id);
	
	public void deleteUser(User User);

	public void resetPassword(User user);

	public List<User> getUsersListByUsername(String username);

	public String saveUserImage(MultipartFile multipartFile, Long userImageId);


}

