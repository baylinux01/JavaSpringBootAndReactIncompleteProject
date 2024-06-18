package com.demo.webapideneme1.services;

import java.util.List;
import java.util.Optional;


import org.springframework.stereotype.Service;

import com.demo.webapideneme1.models.Comment;
import com.demo.webapideneme1.models.Group;
import com.demo.webapideneme1.models.User;
import com.demo.webapideneme1.repositories.GroupRepository;

@Service
public class GroupService {
	GroupRepository groupRepository;
	UserService userService;
	CommentService commentService;
	

	public GroupService(GroupRepository groupRepository, UserService userService,CommentService commentService) {
		super();
		this.groupRepository = groupRepository;
		this.userService = userService;
		this.commentService=commentService;
	}

	public Group getOneGroupById(Long groupId) {
		
		Group group=groupRepository.findById(groupId).orElse(null);
		if(group!=null)
		{
			
			return group;
		}
		return null;
	}

	public void saveGroup(Group group) {
		groupRepository.save(group);
		
	}

	

	public List<Group> getAllGroups() {
		List<Group> groups=groupRepository.findAll();
		
		return groups;
	}

	public void deleteGroupById(Group group) {
		List<Comment> comments=commentService.getCommentsOfAGroup(group.getId());
		while(comments.size()>0)
		{
			
			comments.get(0).setGroup(null);
			comments.get(0).setOwner(null);
			comments.get(0).setQuotedComment(null);
			commentService.saveComment(comments.get(0));
			commentService.deleteComment(comments.get(0).getId());
			comments.remove(comments.get(0));
			
		}
		groupRepository.delete(group);
		
	}

	public List<Group> getGroupsOfAOwner(long userId) {
		List<Group> groups=groupRepository.findByOwnerId(userId);
		return groups;
	}

	public List<Group> getGroupsOfAMember(long userId) {
		User member=userService.getOneUserById(userId);
		if(member!=null)
		{
			if(member!=null)
			{
				List<Group> groups=groupRepository.findByMembersContaining(member);
				return groups;
			}else return null;
		}else
		
		return null;
	}
	

}
