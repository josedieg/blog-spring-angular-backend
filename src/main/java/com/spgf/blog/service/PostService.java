package com.spgf.blog.service;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.spgf.blog.model.Post;
import com.spgf.blog.model.User;

public interface PostService {
	
	public Post savePost(User user, HashMap<String, String> request, String postImageName);
	
	public List<Post> postList();
	
	public Post getPostById(Long id);
	
	public List<Post> findPostByUsername(String username);
	
	public Post deletePost(Post post);
	
	public String savePostImage(HttpServletRequest request, String fileName);
	
}
