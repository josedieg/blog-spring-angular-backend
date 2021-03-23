package com.spgf.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spgf.blog.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	public Role findRoleByName(String name);

}
