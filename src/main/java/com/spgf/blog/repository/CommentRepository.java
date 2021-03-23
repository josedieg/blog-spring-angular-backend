package com.spgf.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spgf.blog.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}