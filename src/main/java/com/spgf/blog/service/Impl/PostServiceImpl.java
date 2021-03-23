package com.spgf.blog.service.Impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.spgf.blog.model.Post;
import com.spgf.blog.model.User;
import com.spgf.blog.repository.PostRepository;
import com.spgf.blog.service.PostService;
import com.spgf.blog.utility.Constants;

@Service
@Transactional
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;

	@Override
	public Post savePost(User user, HashMap<String, String> request, String postImageName) {
		String caption = request.get("caption");
		String location = request.get("location");
		Post post = new Post();
		post.setCaption(caption);
		post.setLocation(location);
		post.setUsername(user.getUsername());
		post.setPostedDate(new Date());
		post.setUserImageId(user.getId());
		user.setPost(post);
		postRepository.save(post);

		return post;
	}

	@Override
	public List<Post> postList() {
		return postRepository.findAll();
	}

	@Override
	public Post getPostById(Long id) {
		return postRepository.findPostById(id);
	}

	@Override
	public List<Post> findPostByUsername(String username) {
		return postRepository.findPostByUsername(username);
	}

	@Override
	public Post deletePost(Post post) {
		try {
			Files.deleteIfExists(Paths.get(Constants.POST_FOLDER + "/" + post.getName() + "/" + ".png"));
			postRepository.deletePostById(post.getId());
			return post;
		} catch (Exception e) {

		}
		return null;
	}

	@Override
	public String savePostImage(HttpServletRequest request, String fileName) {
		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
		Iterator<String> it = multipartHttpServletRequest.getFileNames();
		MultipartFile multipartFile = multipartHttpServletRequest.getFile(it.next());

		try {
			byte[] bytes = multipartFile.getBytes();
			Path path = Paths.get(Constants.POST_FOLDER + fileName + ".png");
			Files.write(path, bytes, StandardOpenOption.CREATE);
		} catch (IOException e) {
			System.out.println("Error occured. Photo not saved!");
			return "Error occured. Photo not saved!";
		}
		System.out.println("Photo saved successfully!");
		return "Photo saved successfully!";
	}
	
	@Override
	public String savePostImage(MultipartFile multipartFile, String fileName) {
		
		try {
			byte[] bytes = multipartFile.getBytes();
			Path path = Paths.get(Constants.POST_FOLDER + fileName + ".png");
			Files.write(path, bytes, StandardOpenOption.CREATE);
		} catch (IOException e) {
			System.out.println("Error occured. Photo not saved!");
			return "Error occured. Photo not saved!";
		}
		System.out.println("Photo saved successfully!");
		return "Photo saved successfully!";
	}

}
