package com.spgf.blog.service;

import com.spgf.blog.model.Post;

public interface CommentService {

	public void saveComment(Post post, String username, String content);

}
