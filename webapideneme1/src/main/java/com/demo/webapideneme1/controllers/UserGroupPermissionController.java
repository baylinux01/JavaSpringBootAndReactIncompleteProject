package com.demo.webapideneme1.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.webapideneme1.services.UserGroupPermissionService;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
@RequestMapping("/usergrouppermissions")
public class UserGroupPermissionController {
	
	UserGroupPermissionService userGroupPermissionService;
	
	@Autowired
	public UserGroupPermissionController(UserGroupPermissionService userGroupPermissionService) {
		super();
		this.userGroupPermissionService = userGroupPermissionService;
	}
	@PutMapping("/addsendmessagepermission")
	public String addSendMessagePermissions(HttpServletRequest request,Long userId,Long groupId)
	{
		return userGroupPermissionService.addSendMessagePermission(request,userId,groupId);
	}
	@PutMapping("/removesendmessagepermission")
	public String removeSendMessagePermissions(HttpServletRequest request,Long userId,Long groupId)
	{
		return userGroupPermissionService.removeSendMessagePermission(request,userId,groupId);
	}
	@PutMapping("/addsendmediapermission")
	public String addSendMediaPermissions(HttpServletRequest request,Long userId,Long groupId)
	{
		return userGroupPermissionService.addSendMediaPermission(request,userId,groupId);
	}
	@PutMapping("/removesendmediapermission")
	public String removeSendMediaPermissions(HttpServletRequest request,Long userId,Long groupId)
	{
		return userGroupPermissionService.removeSendMediaPermission(request,userId,groupId);
	}
	
	@GetMapping("/getpermissionsofauserforagroup")
	public String getPermissionsOfAUserForAGroup(HttpServletRequest request,Long userId,Long groupId)
	{
		return userGroupPermissionService.getPermissionsOfAUserForAGroup(request,userId,groupId);
	}
	
	

}
