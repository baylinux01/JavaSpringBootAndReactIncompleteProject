package com.demo.webapideneme1.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;


import com.demo.webapideneme1.models.Comment;
import com.demo.webapideneme1.models.Group;
import com.demo.webapideneme1.repositories.CommentRepository;

@Service
public class CommentService {
	CommentRepository commentRepository;
	

	

	public CommentService(CommentRepository commentRepository) {
		super();
		this.commentRepository = commentRepository;
		
	}

	public String saveComment(Comment comment) {
		commentRepository.save(comment);
		return "Comment succesfully created";
	}

	public List<Comment> getAllComments() {
		List<Comment> comments=commentRepository.findAll();
		return comments;
	}

	public Comment getOneCommentById(Long commentId) {
		Comment comment=commentRepository.findById(commentId).orElse(null);
		
		return comment;
	}

	public String deleteComment(long commentId) {
		commentRepository.deleteById(commentId);
		return "Comment succesfully deleted";
	}

	public List<Comment> getCommentsOfAGroup(long groupId) {
		//Group group=groupService.getOneGroupById(groupId);
		List<Comment> comments=commentRepository.findByGroupId(groupId);
		return comments;
	}

	
	

}
