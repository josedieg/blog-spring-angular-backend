package com.spgf.blog.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spgf.blog.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

	@Query("SELECT p FROM Post p ORDER BY p.postedDate DESC")
	public List<Post> findAll();

	@Query("SELECT p FROM Post WHERE p.username =:username ORDER BY p.postedDate DESC")
	public List<Post> findPostByUsername(@Param("username") String username);

	@Query("SELECT p FROM Post p WHERE p.id=:idPost")
	public Optional<Post> findById(@Param("idPost") Long id);
	
	public Post deletePostById(Long id);
}
