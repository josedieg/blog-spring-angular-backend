package com.spgf.blog.service.Impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spgf.blog.model.User;
import com.spgf.blog.model.UserRole;
import com.spgf.blog.service.AccountService;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	AccountService accountService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User User = accountService.findByUsername(username);
		if (User == null) {
			throw new UsernameNotFoundException("Username " + username + " was not found");
		}
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		Set<UserRole> userRoles = User.getUserRoles();
		userRoles.forEach(userRole -> {
			authorities.add(new SimpleGrantedAuthority(userRoles.toString()));
		});
		return new org.springframework.security.core.userdetails.User(User.getUsername(), User.getPassword(), authorities);
	}

}
