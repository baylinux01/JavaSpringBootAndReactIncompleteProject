package com.demo.webapideneme1.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.demo.webapideneme1.models.ByteArrayMultipartFile;
import com.demo.webapideneme1.models.Group;
import com.demo.webapideneme1.models.User;
import com.demo.webapideneme1.models.UserGroupMedia;
import com.demo.webapideneme1.services.UserGroupMediaService;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
@RequestMapping("/usergroupmedias")
public class UserGroupMediaController {

	UserGroupMediaService userGroupMediaService;
	@Autowired
	public UserGroupMediaController(UserGroupMediaService userGroupMediaService) {
		super();
		this.userGroupMediaService = userGroupMediaService;
	}
	@PostMapping("/sendmediatoagroupasbytes")
	public String sendMediaToAGroupAsBytes(HttpServletRequest request,
			String name,String originalFileName,String contentType, byte[] multipartFileBytesToBeUploaded, Long groupId)
	{
		MultipartFile multipartFileToBeUploaded=new ByteArrayMultipartFile
				(name,originalFileName,contentType,multipartFileBytesToBeUploaded);
		return userGroupMediaService.sendMediaToAGroup(request,multipartFileToBeUploaded,groupId);
	}
	@PostMapping("/sendmediatoagroup/{groupId}")
	public String sendMediaToAGroup(HttpServletRequest request,MultipartFile multipartFileToBeUploaded,@PathVariable Long groupId)
	{
		
		return userGroupMediaService.sendMediaToAGroup(request,multipartFileToBeUploaded,groupId);
	}
	@GetMapping("/getmediasofagroup")
	public List<UserGroupMedia> getMediasOfAGroup(HttpServletRequest request,Long groupId)
	{
		return userGroupMediaService.getMediasOfAGroup(request,groupId);
	}
}
