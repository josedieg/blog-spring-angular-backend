package com.spgf.blog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, nullable = false)
	private Integer id;
	
	private String username;
	
	@Column(columnDefinition = "text")
	private String content;
	
	private String postedDatge;

	public Comment() {

	}

	public Comment(Integer id, String username, String content, String postedDatge) {
		this.id = id;
		this.username = username;
		this.content = content;
		this.postedDatge = postedDatge;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPostedDatge() {
		return postedDatge;
	}

	public void setPostedDatge(String postedDatge) {
		this.postedDatge = postedDatge;
	}

}
