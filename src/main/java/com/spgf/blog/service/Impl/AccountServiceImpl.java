package com.spgf.blog.service.Impl;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.spgf.blog.model.Role;
import com.spgf.blog.model.User;
import com.spgf.blog.model.UserRole;
import com.spgf.blog.repository.RoleRepository;
import com.spgf.blog.repository.UserRepository;
import com.spgf.blog.service.AccountService;
import com.spgf.blog.utility.Constants;
import com.spgf.blog.utility.EmailConstructor;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

	@Autowired
	AccountService accountService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private UserRepository UserRepository;

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private EmailConstructor emailConstructor;

	@Autowired
	private JavaMailSender mailSender;

	@Override
	@Transactional
	public User saveUser(String name, String username, String email) {
		String password = RandomStringUtils.randomAlphanumeric(10);
		String encryptedPassword = bCryptPasswordEncoder.encode(password);
		User User = new User();
		User.setPassword(encryptedPassword);
		User.setName(name);
		User.setUsername(username);
		User.setEmail(email);
		Set<UserRole> userRoles = new HashSet<>();
		userRoles.add(new UserRole(User, accountService.findUserRoleByName("USER")));
		User.setUserRoles(userRoles);
		UserRepository.save(User);
		byte[] bytes;
		try {
			bytes = Files.readAllBytes(Constants.TEMP_USER.toPath());
			String fileName = User.getId() + ".png";
			Path path = Paths.get(Constants.USER_FOLDER + fileName);
			Files.write(path, bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		mailSender.send(emailConstructor.constructNewUserEmail(User, password));
		return User;
	}

	@Override
	public void updateUserPassword(User User, String newpassword) {
		String encryptedPassword = bCryptPasswordEncoder.encode(newpassword);
		User.setPassword(encryptedPassword);
		UserRepository.save(User);
		mailSender.send(emailConstructor.constructResetPasswordEmail(User, newpassword));
	}

	@Override
	public Role saveRole(Role role) {
		return roleRepo.save(role);
	}

	@Override
	public User findByUsername(String username) {
		return UserRepository.findByUsername(username);
	}

	@Override
	public User findByEmail(String userEmail) {
		return UserRepository.findByEmail(userEmail);
	}

	@Override
	public List<User> userList() {
		return UserRepository.findAll();
	}

	@Override
	public Role findUserRoleByName(String name) {
		return roleRepo.findRoleByName(name);
	}

	@Override
	public User simpleSaveUser(User user) {
		UserRepository.save(user);
		mailSender.send(emailConstructor.constructUpdateUserProfileEmail(user));
		return user;

	}

	@Override
	public User updateUser(User user, HashMap<String, String> request) {
		String name = request.get("name");
		// String username = request.get("username");
		String email = request.get("email");
		String bio = request.get("bio");
		user.setName(name);
		// User.setUsername(username);
		user.setEmail(email);
		user.setBio(bio);
		UserRepository.save(user);
		mailSender.send(emailConstructor.constructUpdateUserProfileEmail(user));
		return user;

	}

	@Override
	public User findUserById(Long id) {
		return UserRepository.findUserById(id);
	}

	@Override
	public void deleteUser(User User) {
		UserRepository.delete(User);

	}

	@Override
	public void resetPassword(User user) {
		String password = RandomStringUtils.randomAlphanumeric(10);
		String encryptedPassword = bCryptPasswordEncoder.encode(password);
		user.setPassword(encryptedPassword);
		UserRepository.save(user);
		mailSender.send(emailConstructor.constructResetPasswordEmail(user, password));

	}

	@Override
	public List<User> getUsersListByUsername(String username) {
		return UserRepository.findByUsernameContaining(username);
	}

	@Override
	public String saveUserImage(MultipartFile multipartFile, Long userImageId) {
		/*
		 * MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)
		 * request; Iterator<String> it = multipartRequest.getFileNames(); MultipartFile
		 * multipartFile = multipartRequest.getFile(it.next());
		 */
		byte[] bytes;
		try {
			Files.deleteIfExists(Paths.get(Constants.USER_FOLDER + "/" + userImageId + ".png"));
			bytes = multipartFile.getBytes();
			Path path = Paths.get(Constants.USER_FOLDER + userImageId + ".png");
			Files.write(path, bytes);
			return "User picture saved to server";
		} catch (IOException e) {
			return "User picture Saved";
		}
	}

}
