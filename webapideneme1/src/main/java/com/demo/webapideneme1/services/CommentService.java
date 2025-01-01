package com.demo.webapideneme1.services;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.demo.webapideneme1.models.Comment;
import com.demo.webapideneme1.models.Group;
import com.demo.webapideneme1.models.User;
import com.demo.webapideneme1.models.UserGroupPermission;
import com.demo.webapideneme1.repositories.CommentRepository;
import com.demo.webapideneme1.repositories.GroupRepository;
import com.demo.webapideneme1.repositories.UserGroupPermissionRepository;
import com.demo.webapideneme1.repositories.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class CommentService {
	CommentRepository commentRepository;
	UserRepository userRepository;
	GroupRepository groupRepository;
	UserGroupPermissionRepository userGroupPermissionRepository;

	
	@Autowired
	public CommentService(CommentRepository commentRepository
			,UserRepository userRepository
			,GroupRepository groupRepository
			,UserGroupPermissionRepository userGroupPermissionRepository) {
		super();
		this.commentRepository = commentRepository;
		this.userRepository=userRepository;
		this.groupRepository=groupRepository;
		this.userGroupPermissionRepository=userGroupPermissionRepository;
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

	public String deleteComment(HttpServletRequest request,long commentId) {
		/*jwt olmadan requestten kullanıcı adını alma kodları başlangıcı*/		
		Principal pl=request.getUserPrincipal();
		String username=pl.getName();
		/*jwt olmadan requestten kullanıcı adını alma kodları sonu*/
		User user=userRepository.findByUsername(username);
		Comment comment=commentRepository.findById(commentId).orElse(null);
		if(comment!=null&&(comment.getOwner()==user||user.getRoles().contains("ADMIN")))
		{
			commentRepository.deleteById(commentId);
			return "Comment succesfully deleted";
		}
		else return "fail";
	}

	public List<Comment> getCommentsOfAGroup(HttpServletRequest request,long groupId) {
		/*jwt olmadan requestten kullanıcı adını alma kodları başlangıcı*/		
		Principal pl=request.getUserPrincipal();
		String username=pl.getName();
		/*jwt olmadan requestten kullanıcı adını alma kodları sonu*/
		User user=userRepository.findByUsername(username);
		Group group=groupRepository.findById(groupId).orElse(null);
		if(group.getMembers().contains(user))
		{
			List<Comment> comments=commentRepository.findByGroupId(groupId);
			return comments;
		}
		else return null;
		
		
	}

	public String createComment(HttpServletRequest request, String content, Long commentIdToBeQuoted, Long groupId) {
		/*jwt olmadan requestten kullanıcı adını alma kodları başlangıcı*/		
		Principal pl=request.getUserPrincipal();
		String username=pl.getName();
		/*jwt olmadan requestten kullanıcı adını alma kodları sonu*/
		User user=userRepository.findByUsername(username);
		Group group= groupRepository.findById(groupId).orElse(null);
		String result="";
		Comment commentToBeQuoted=null;
		if(commentIdToBeQuoted!=null)
		commentToBeQuoted=commentRepository.findById(commentIdToBeQuoted).orElse(null);
		Comment comment;
		
		if(user!=null&& group!=null
				&& group.getMembers().contains(user)
		)
		{
			List<UserGroupPermission> ugp=userGroupPermissionRepository.findByUserAndGroup(user, group);
			if(ugp.size()==1)
			{
				String permissions=ugp.get(0).getPermissions();
				if(permissions.contains("SENDMESSAGE"))
				{
					comment=new Comment();
					comment.setOwner(user);
					comment.setGroup(group);
					comment.setContent(content);
					if(commentToBeQuoted!=null)
					{
						if(!commentToBeQuoted.getOwner().getBannedUsers().contains(user)
								&&!user.getBannedUsers().contains(commentToBeQuoted.getOwner()))
						{
							if(commentToBeQuoted.getGroup()==group)
							comment.setQuotedComment(commentToBeQuoted);
						}else comment.setQuotedComment(null);
					}
					else comment.setQuotedComment(null);
					commentRepository.save(comment);
					return "comment successfully created";
				}
				else
				{
					return "permissions not found";
				}
			}
			else
			{
				return "userGroupPermissions object not found";
			}
			
			
		} else return "User or group not found";
		
	}

	public String updateComment(HttpServletRequest request, Long commentId, String newcontent) {
		/*jwt olmadan requestten kullanıcı adını alma kodları başlangıcı*/		
		Principal pl=request.getUserPrincipal();
		String username=pl.getName();
		/*jwt olmadan requestten kullanıcı adını alma kodları sonu*/
		User user=userRepository.findByUsername(username);
		Comment comment=commentRepository.findById(commentId).orElse(null);
		if(comment!=null&&comment.getOwner()==user)
		{
			comment.setContent(newcontent);
			comment.setCommentEditDate(new Date());
			commentRepository.save(comment);
			
			return "Comment succesfully updated";
		}else return "fail";
	}

	
	

}
