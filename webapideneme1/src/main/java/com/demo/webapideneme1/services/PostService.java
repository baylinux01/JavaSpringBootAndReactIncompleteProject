package com.demo.webapideneme1.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.webapideneme1.models.Post;
import com.demo.webapideneme1.repositories.GroupRepository;
import com.demo.webapideneme1.repositories.PostRepository;
import com.demo.webapideneme1.models.Group;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class PostService {
	PostRepository postRepository;
	GroupRepository groupRepository;
	@Autowired
	public PostService(PostRepository postRepository,GroupRepository groupRepository) {
		super();
		this.postRepository = postRepository;
		this.groupRepository=groupRepository;
	}
	public List<Post> getAllPosts(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return postRepository.findAll();
	}
	public List<Post> getPostsOfAGroup(HttpServletRequest request, Long groupId) {
		// TODO Auto-generated method stub
		Group group=groupRepository.findById(groupId).orElse(null);
		if(group!=null)
		{
			return postRepository.findByGroup(group);
		}
		else
		{
			return null;
		}
		
	}
	
	

}
