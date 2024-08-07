package com.demo.webapideneme1.controllers;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.demo.webapideneme1.models.User;
import com.demo.webapideneme1.services.UserService;

import io.jsonwebtoken.security.InvalidKeyException;
@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {
	UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}
	
	
	
	@DeleteMapping("/deleteconnection")
	public String deleteConnection(Long deletingUserId,Long userToBeDeletedId) 
	{
		String result=userService.deleteConnection(deletingUserId,userToBeDeletedId);
		return result;
	}
	
	@PostMapping("/acceptconnection")
	public String acceptConnection(Long acceptingUserId,Long userToBeAcceptedId) 
	{
		String result=userService.acceptConnection(acceptingUserId,userToBeAcceptedId);
		return result;
	}
	
	@PostMapping("/banuser")
	public String banUser(Long banningUserId,Long userToBeBannedId) 
	{
		String result=userService.banUser(banningUserId,userToBeBannedId);
		return result;
	}
	
	@GetMapping("/getconnectionsofauser")
	public List<User> getConnectionsOfAUser(Long userId)
	{
		return userService.getConnectionsOfAUser(userId);
	}
	
	@GetMapping("/getbannedusersofauser")
	public List<User> getBannedUsersOfAUser(Long userId)
	{
		return userService.getBannedUsersOfAUser(userId);
	}

	@GetMapping("/getsearchedusers")
	public List<User> getSearchedUsers(String searchedWords)
	{
		return userService.getSearchedUsers(searchedWords);
	}
	@GetMapping("/getallusers")
	public List<User> getAllUsers()
	{
		
		List<User> users= userService.getAllUsers();
		return users;
		
	}
	@GetMapping("/getoneuserbyid")
	public User getOneUserById(long userId)
	{
		User user=userService.getOneUserById(userId);
		if(user!=null)
		{
			return user;
			
		}else return null;
		
	}
	@GetMapping("/getoneuserbyusername")
	public User getOneUserByUsername(String username)
	{
		User user=userService.getOneUserByUsername(username);
		return user;
	}
	@PostMapping("/createuser")
	public  String createUser(String name, String surname, String username,  
			String password, MultipartFile userimage, String birthdate) throws IOException 
	{
		
		
		String result=userService.createUser( name,surname,username,  
				password, userimage,birthdate);
		return result;
		
		
	}
	@PostMapping("/enteruser")
	public String enterUser(String username,String password) throws InvalidKeyException, NoSuchAlgorithmException 
	{
		return userService.enterUser(username,password);
		
	}
	@PostMapping("/exituser")
	public User exitUser()
	{
		return null;
	}
	@PutMapping("/updateuser")
	public String updateUser
	(long id, String newname,String newsurname, String newusername,
		MultipartFile newuserimage,String newbirthdate) 
	throws IOException
	{
		String result=userService.updateUser(id, newname,newsurname, newusername,newuserimage,newbirthdate);
		return result;
	}
	@DeleteMapping
	public String deleteUser(long id)
	{
		String result=userService.deleteUser(id);
		return result;
	}
	
	@PutMapping("/changeuserpassword")
	public String changeUserPassword(long userId, String newPassword)
	{
		
		String result=userService.changeUserPassword(userId,newPassword);
			return result;
		
	}
	

}
