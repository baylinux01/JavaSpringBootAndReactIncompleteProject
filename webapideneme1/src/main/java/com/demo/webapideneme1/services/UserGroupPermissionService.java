package com.demo.webapideneme1.services;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.webapideneme1.models.Group;
import com.demo.webapideneme1.models.User;
import com.demo.webapideneme1.models.UserGroupPermission;
import com.demo.webapideneme1.repositories.GroupRepository;
import com.demo.webapideneme1.repositories.UserGroupPermissionRepository;
import com.demo.webapideneme1.repositories.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserGroupPermissionService {
	
	UserGroupPermissionRepository userGroupPermissionRepository;
	UserRepository userRepository;
	GroupRepository groupRepository;
	@Autowired
	public UserGroupPermissionService(UserGroupPermissionRepository userGroupPermissionRepository,
			UserRepository userRepository, GroupRepository groupRepository) {
		super();
		this.userGroupPermissionRepository = userGroupPermissionRepository;
		this.userRepository = userRepository;
		this.groupRepository = groupRepository;
	}
	public String addSendMessagePermission(HttpServletRequest request,Long userId, Long groupId) {
		/*jwt olmadan requestten kullanıcı adını alma kodları başlangıcı*/		
		Principal pl=request.getUserPrincipal();
		String username=pl.getName();
		/*jwt olmadan requestten kullanıcı adını alma kodları sonu*/
		User user1=userRepository.findByUsername(username);
		User user2=userRepository.findById(userId).orElse(null);
		Group group=groupRepository.findById(groupId).orElse(null);
		if(user1!=null&&user2!=null&&group!=null&&group.getOwner()==user1)
		{
			List<UserGroupPermission> ugp=userGroupPermissionRepository.findByUserAndGroup(user2, group);
			if(ugp.size()==1)
			{
				String permissions=ugp.get(0).getPermissions();
				if(!permissions.contains("SENDMESSAGE"))
				permissions="SENDMESSAGE"+permissions;
				permissions=permissions.replaceAll("--", "-");
				ugp.get(0).setPermissions(permissions);
				userGroupPermissionRepository.save(ugp.get(0));
				return "SENDMESSAGE permission successfully added";
			}
			else
			{
				return "userGroupPermission object not found";
			}
			
		}
		return "an error occurred";
	}
	public String removeSendMessagePermission(HttpServletRequest request, Long userId, Long groupId) {
		/*jwt olmadan requestten kullanıcı adını alma kodları başlangıcı*/		
		Principal pl=request.getUserPrincipal();
		String username=pl.getName();
		/*jwt olmadan requestten kullanıcı adını alma kodları sonu*/
		User user1=userRepository.findByUsername(username);
		User user2=userRepository.findById(userId).orElse(null);
		Group group=groupRepository.findById(groupId).orElse(null);
		if(user1!=null&&user2!=null&&group!=null&&group.getOwner()==user1)
		{
			List<UserGroupPermission> ugp=userGroupPermissionRepository.findByUserAndGroup(user2, group);
			if(ugp.size()==1)
			{
				String permissions=ugp.get(0).getPermissions();
				if(permissions.contains("SENDMESSAGE"))
				permissions=permissions.replaceAll("-SENDMESSAGE","");
				permissions=permissions.replaceAll("SENDMESSAGE","");
				permissions=permissions.replaceAll("--", "-");
				ugp.get(0).setPermissions(permissions);
				userGroupPermissionRepository.save(ugp.get(0));
				return "SENDMESSAGE permission successfully removed";
			}
			else
			{
				return "userGroupPermission object not found";
			}
			
		}
		return "an error occurred";
	}
	public String getPermissionsOfAUserForAGroup(HttpServletRequest request, Long userId, Long groupId) {
		/*jwt olmadan requestten kullanıcı adını alma kodları başlangıcı*/		
		Principal pl=request.getUserPrincipal();
		String username=pl.getName();
		/*jwt olmadan requestten kullanıcı adını alma kodları sonu*/
		User user1=userRepository.findByUsername(username);
		User user2=userRepository.findById(userId).orElse(null);
		Group group=groupRepository.findById(groupId).orElse(null);
		if(user1!=null&&user2!=null&&group!=null)
		{
			List<UserGroupPermission> ugp=userGroupPermissionRepository.findByUserAndGroup(user2, group);
			if(ugp.size()==1)
			{
				String permissions=ugp.get(0).getPermissions();
				
				return permissions;
			}
			else
			{
				return "userGroupPermission object not found";
			}
			
		}
		return "an error occurred";
	}
	
	
	

}
