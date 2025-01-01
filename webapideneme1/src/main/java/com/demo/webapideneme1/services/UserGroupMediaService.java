package com.demo.webapideneme1.services;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.demo.webapideneme1.models.Group;
import com.demo.webapideneme1.models.User;
import com.demo.webapideneme1.models.UserGroupMedia;
import com.demo.webapideneme1.repositories.GroupRepository;
import com.demo.webapideneme1.repositories.UserGroupMediaRepository;
import com.demo.webapideneme1.repositories.UserGroupPermissionRepository;
import com.demo.webapideneme1.repositories.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserGroupMediaService {

	UserGroupMediaRepository userGroupMediaRepository;
	UserRepository userRepository;
	GroupRepository groupRepository;
	FileTransferService fileTransferService;
	@Autowired
	public UserGroupMediaService(UserGroupMediaRepository userGroupMediaRepository, UserRepository userRepository,
			GroupRepository groupRepository, FileTransferService fileTransferService) {
		super();
		this.userGroupMediaRepository = userGroupMediaRepository;
		this.userRepository = userRepository;
		this.groupRepository = groupRepository;
		this.fileTransferService = fileTransferService;
	}
	public String sendMediaToAGroup(HttpServletRequest request, MultipartFile multipartFileToBeUploaded, Long groupId) {
		/*jwt olmadan requestten kullanıcı adını alma kodları başlangıcı*/		
		Principal pl=request.getUserPrincipal();
		String username=pl.getName();
		/*jwt olmadan requestten kullanıcı adını alma kodları sonu*/
		User user1=userRepository.findByUsername(username);
		Group group=groupRepository.findById(groupId).orElse(null);
		if(user1!=null&&group!=null&&multipartFileToBeUploaded!=null)
		{
			
			try {
				fileTransferService.handleUpload(multipartFileToBeUploaded);
				UserGroupMedia ugm=new UserGroupMedia();
				ugm.setUser(user1);
				ugm.setGroup(group);
				ugm.setMedia_address(fileTransferService.getFilestorageaddress()+File.separator+multipartFileToBeUploaded.getOriginalFilename());
				ugm.setContent_type(multipartFileToBeUploaded.getContentType());
				userGroupMediaRepository.save(ugm);
				
				return "success";
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "there was an error";
			}
		}
		else
		{
			return "user, group or file not found";
		}
	}
	public List<UserGroupMedia> getMediasOfAGroup(HttpServletRequest request, Long groupId) {
		/*jwt olmadan requestten kullanıcı adını alma kodları başlangıcı*/		
		Principal pl=request.getUserPrincipal();
		String username=pl.getName();
		/*jwt olmadan requestten kullanıcı adını alma kodları sonu*/
		User user1=userRepository.findByUsername(username);
		Group group=groupRepository.findById(groupId).orElse(null);
		if(user1!=null&&group!=null)
		{
			List<UserGroupMedia> list=userGroupMediaRepository.findByGroup(group);
			return list;
		}
		else
		{
			return new ArrayList();
		}
	}
	
	
	
}
