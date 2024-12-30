package com.demo.webapideneme1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
	

}
