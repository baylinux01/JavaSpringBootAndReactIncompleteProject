package com.demo.webapideneme1.services;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class GroupService {
	GroupRepository groupRepository;
	UserRepository userRepository;
	CommentRepository commentRepository;
	UserGroupPermissionRepository userGroupPermissionRepository;
	
	@Autowired
	public GroupService(GroupRepository groupRepository
			,UserRepository userRepository,CommentRepository commentRepository
			,UserGroupPermissionRepository userGroupPermissionRepository) {
		super();
		this.groupRepository = groupRepository;
		this.userRepository = userRepository;
		this.commentRepository=commentRepository;
		this.userGroupPermissionRepository=userGroupPermissionRepository;
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
	@Transactional
	public String deleteGroupById(HttpServletRequest request,long groupId) {
		/*jwt olmadan requestten kullanıcı adını alma kodları başlangıcı*/		
		Principal pl=request.getUserPrincipal();
		String username=pl.getName();
		/*jwt olmadan requestten kullanıcı adını alma kodları sonu*/
		User user=userRepository.findByUsername(username);
		Group group=groupRepository.findById(groupId).orElse(null);
		if(group!=null&&(group.getOwner()==user||user.getRoles().contains("ADMIN")))
		{
			List<Comment> comments=commentRepository.findByGroupId(groupId);
			while(comments.size()>0)
			{
				
				comments.get(0).setGroup(null);
				comments.get(0).setOwner(null);
				comments.get(0).setQuotedComment(null);
				commentRepository.save(comments.get(0));
				commentRepository.delete(comments.get(0));
				comments.remove(comments.get(0));
				
			}
			groupRepository.delete(group);
			return "success";
		}else return "fail";
		
		
	}

	public List<Group> getGroupsOfAOwner(long userId) {
		List<Group> groups=groupRepository.findByOwnerId(userId);
		return groups;
	}

	public List<Group> getGroupsOfAMember(long userId) {
		User member=userRepository.findById(userId).orElse(null);
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

	public String createGroup(HttpServletRequest request, String name) {
		/*jwt olmadan requestten kullanıcı adını alma kodları başlangıcı*/		
		Principal pl=request.getUserPrincipal();
		String username=pl.getName();
		/*jwt olmadan requestten kullanıcı adını alma kodları sonu*/
		User owner=userRepository.findByUsername(username);
		if(!name.matches("^[öüÖÜĞğşŞçÇıİ|a-z|A-Z]{1,20}(\\s[öüÖÜĞğşŞçÇıİ|a-z|A-Z]{1,20}){0,10}$"))
			return "Grup is not suitable to format. Group could not be created";
		List<Group> groups=groupRepository.findAll();
		for(Group g : groups)
		{
			if(g.getName().equals(name)) return "A group name cannot be the same as the name of another group. Group creation unsuccessful";
		}
		if(owner==null) return "Group cannot be created. Group owner not found";
		if(owner!=null)
		{
			Group group=new Group();
			group.setOwner(owner);
			if(group.getMembers()!=null)
			{
				group.getMembers().add(owner);
				group.setMembers(group.getMembers());
			}else
			{
				List<User> members=new ArrayList<User>();
				members.add(owner);
				group.setMembers(members);
			}
			UserGroupPermission usgrpe=userGroupPermissionRepository.findByUserAndGroup(owner,group);
			if(usgrpe==null)
			{
				UserGroupPermission ugp=new UserGroupPermission();
				ugp.setUser(owner);
				ugp.setGroup(group);
				String per=ugp.getPermissions();
				per+="-ADDUSER-BANUSER";
				ugp.setPermissions(per);
				userGroupPermissionRepository.save(ugp);
			}
			 
			group.setName(name);
			groupRepository.save(group);
			return "Group successfully created";
		
		}else return "Group could not be created";
	}

	public String updateGroupName(HttpServletRequest request, long groupId, String newgroupname) {
		/*jwt olmadan requestten kullanıcı adını alma kodları başlangıcı*/		
		Principal pl=request.getUserPrincipal();
		String username=pl.getName();
		/*jwt olmadan requestten kullanıcı adını alma kodları sonu*/
		User user=userRepository.findByUsername(username);
		Group group=groupRepository.findById(groupId).orElse(null);
		if(user!=null && group!=null && user==group.getOwner() )
		{
			group.setName(newgroupname);
			groupRepository.save(group);
			return "Group name successfully updated";
		}
		return "Group name cannot be updated";
	}

	@Transactional
	public String beAMemberOfGroup(HttpServletRequest request, long groupId) {
		/*jwt olmadan requestten kullanıcı adını alma kodları başlangıcı*/		
		Principal pl=request.getUserPrincipal();
		String username=pl.getName();
		/*jwt olmadan requestten kullanıcı adını alma kodları sonu*/
		User user=userRepository.findByUsername(username);
		Group group=groupRepository.findById(groupId).orElse(null);
		if(group!=null&&user!=null)
		{
			if(group.getOwner()!=user&&!group.getMembers().contains(user))
			{
				UserGroupPermission usgrpe=userGroupPermissionRepository.findByUserAndGroup(user,group);
				if(usgrpe==null)
				{
					UserGroupPermission ugp=new UserGroupPermission();
					ugp.setUser(user);
					ugp.setGroup(group);
					userGroupPermissionRepository.save(ugp);
				}
				group.getMembers().add(user);
				groupRepository.save(group);
				return "Process is successful";
			}
			else return "User is already a member or owner of the group";
		}else return "Group not found";
	}

	public String exitGroup(HttpServletRequest request, long groupId) {
		/*jwt olmadan requestten kullanıcı adını alma kodları başlangıcı*/		
		Principal pl=request.getUserPrincipal();
		String username=pl.getName();
		/*jwt olmadan requestten kullanıcı adını alma kodları sonu*/
		User user=userRepository.findByUsername(username);
		Group group=groupRepository.findById(groupId).orElse(null);
		if(group!=null&&user!=null)
		{
			if(group.getOwner()!=user&&group.getMembers().contains(user))
			{
				group.getMembers().remove(user);
				groupRepository.save(group);
				return "Process is successful";
			}
			else return "User is the owner or not a member of the group";
		}else return "Group not found";
	}
	

}
