package com.demo.webapideneme1.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.webapideneme1.models.Post;
import com.demo.webapideneme1.services.PostService;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
@RequestMapping("/posts")
public class PostController {
	
	PostService postService;
	
	@Autowired
	public PostController(PostService postService) {
		super();
		this.postService = postService;
	}
	
	@GetMapping("/getallposts")
	public List<Post> getAllPosts(HttpServletRequest request)
	{
		return postService.getAllPosts(request);
	}
	
	@GetMapping("/getpostsofagroup")
	public List<Post> getPostsOfAGroup(HttpServletRequest request,Long groupId)
	{
		return postService.getPostsOfAGroup(request,groupId);
	}
}
