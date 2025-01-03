package com.demo.webapideneme1.services;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.demo.webapideneme1.models.Group;
import com.demo.webapideneme1.models.Media;
import com.demo.webapideneme1.models.User;
import com.demo.webapideneme1.models.Post;
import com.demo.webapideneme1.repositories.GroupRepository;
import com.demo.webapideneme1.repositories.MediaRepository;
import com.demo.webapideneme1.repositories.PostRepository;
import com.demo.webapideneme1.repositories.UserGroupPermissionRepository;
import com.demo.webapideneme1.repositories.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class MediaService {

	PostRepository postRepository;
	UserRepository userRepository;
	GroupRepository groupRepository;
	FileTransferService fileTransferService;
	MediaRepository mediaRepository;
	@Autowired
	public MediaService(PostRepository postRepository, UserRepository userRepository,
			GroupRepository groupRepository, FileTransferService fileTransferService
			,MediaRepository mediaRepository) {
		super();
		this.postRepository = postRepository;
		this.userRepository = userRepository;
		this.groupRepository = groupRepository;
		this.fileTransferService = fileTransferService;
		this.mediaRepository=mediaRepository;
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
				Post ugm=new Post();
				ugm.setUser(user1);
				ugm.setGroup(group);
				Media media=new Media();
				media.setMedia_address(fileTransferService.getFilestorageaddress()+File.separator+multipartFileToBeUploaded.getOriginalFilename());
				media.setContent_type(multipartFileToBeUploaded.getContentType());
				media.setName(multipartFileToBeUploaded.getOriginalFilename());
				mediaRepository.save(media);
				ugm.setMedia(media);
				ugm.setUser(user1);
				ugm.setGroup(group);
				postRepository.save(ugm);
				
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
	public List<Post> getMediasOfAGroup(HttpServletRequest request, Long groupId) {
		/*jwt olmadan requestten kullanıcı adını alma kodları başlangıcı*/		
		Principal pl=request.getUserPrincipal();
		String username=pl.getName();
		/*jwt olmadan requestten kullanıcı adını alma kodları sonu*/
		User user1=userRepository.findByUsername(username);
		Group group=groupRepository.findById(groupId).orElse(null);
		if(user1!=null&&group!=null)
		{
			List<Post> list=postRepository.findByGroup(group);
			return list;
		}
		else
		{
			return new ArrayList();
		}
	}
	@Transactional
	public String deleteMedia(HttpServletRequest request, Long id) {
		/*jwt olmadan requestten kullanıcı adını alma kodları başlangıcı*/		
		Principal pl=request.getUserPrincipal();
		String username=pl.getName();
		/*jwt olmadan requestten kullanıcı adını alma kodları sonu*/
		User user1=userRepository.findByUsername(username);
		Media media=mediaRepository.findById(id).orElse(null);
		Post post=postRepository.findByMedia(media);
		if(post!=null&&user1!=null&&(post.getUser()==user1||user1.getRoles().contains("ADMIN"))&&media!=null)
		{
			post.setMedia(null);
			post.setUser(null);
			post.setGroup(null);
			postRepository.save(post);
			mediaRepository.delete(media);
			postRepository.delete(post);
			return "media successfully deleted";
		}
		else
		{
			return "error";
		}
	}
	
	
	
}
