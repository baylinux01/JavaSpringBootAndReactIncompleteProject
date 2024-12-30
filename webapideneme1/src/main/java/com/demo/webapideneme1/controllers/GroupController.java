package com.demo.webapideneme1.controllers;


import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.webapideneme1.models.Group;
import com.demo.webapideneme1.models.User;
import com.demo.webapideneme1.services.CommentService;
import com.demo.webapideneme1.services.GroupService;
import com.demo.webapideneme1.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
@CrossOrigin
@RestController
@RequestMapping("/groups")
public class GroupController {
	GroupService groupService;
	UserService userService;
	CommentService commentService;
	
	@Autowired
	public GroupController(GroupService groupService, UserService userService, CommentService commentService) {
		super();
		this.groupService = groupService;
		this.userService = userService;
		this.commentService = commentService;
	}
	@GetMapping("/getallgroups")
	public List<Group> getAllGroups()
	{
		List<Group>groups=groupService.getAllGroups();
		
		return groups;
		
	}
	@GetMapping("/getgroupsofaowner")
	public List<Group> getGroupsOfAOwner(long userId)
	{
		List<Group> groups=groupService.getGroupsOfAOwner(userId);
		return groups;
	}
	@GetMapping("/getgroupsofamember")
	public List<Group> getGroupsOfAMember(long userId)
	{
		List<Group> groups=groupService.getGroupsOfAMember(userId);
		return groups;
	}
	@GetMapping("/getonegroupbyid")
	public Group getOneGroupById(long groupId)
	{
		Group group=groupService.getOneGroupById(groupId);
		return group;
	}
	@GetMapping("/getonegroupmembersbygroupid")
	public List<User> getOneGroupMembersByGroupId(long groupId)
	{
		Group group=groupService.getOneGroupById(groupId);
		return group.getMembers();
	}
	@PostMapping("/creategroup")
	public String createGroup(HttpServletRequest request,String name)
	{
		return groupService.createGroup(request,name);
	}
	@PutMapping("/updategroupname")
	public String updateGroupName(HttpServletRequest request,long groupId,String newgroupname)
	{
		return groupService.updateGroupName(request,groupId,newgroupname);
	}
	@DeleteMapping("/deletegroupbyid")
	public String deleteGroupById(HttpServletRequest request,long groupId)
	{
		
			return groupService.deleteGroupById(request,groupId);
			
	}
	@PostMapping("/beamemberofgroup")
	public String beAMemberOfGroup(HttpServletRequest request,long groupId)
	{
		return groupService.beAMemberOfGroup(request,groupId);
		
	}
	@PostMapping("/exitgroup")
	public String exitGroup(HttpServletRequest request,long groupId)
	{
		return groupService.exitGroup(request,groupId);
		
	}
	
     
	
	

}
