package com.demo.webapideneme1.controllers;

import java.io.IOException;
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
	
	
	
	@PostMapping("/deleteconnection")
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
	@PostMapping("/adduser")
	public  String addUser(String name, String surname, String username,  
			String password, MultipartFile userimage, String birthdate) throws IOException 
	{
		User user=new User();
		user.setName(name);
		user.setSurname(surname);
		user.setUsername(username);
		user.setPassword(password);
		
		int day=0;
		int month=0;
		int year=0000;
		System.out.println(birthdate);
		if(birthdate!=null && !birthdate.equals(""))
		{
			if(birthdate.matches("^(19|20)\\d\\d\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$"))
			{
			
			String[] arr=new String[3];
			arr=birthdate.split("-");
			year=Integer.valueOf(arr[0]);
			month=Integer.valueOf(arr[1]);
			day=Integer.valueOf(arr[2]);
			Date dateuserentered;
			Date currentdate;
		if((month==2||month==4||month==6||month==9||month==11)&&day==31)
		{
			user.setBirthDate(null);
		}
		else if(month==2 && day==30)
		{
			user.setBirthDate(null);
		}
		else if(month==2 && day==29 && year%4!=0)
		{
			user.setBirthDate(null);
		}
		else
		{
		
		LocalDate localDate=LocalDate.of(year, month, day);
		//user.setBirthDate(new Date());
		dateuserentered=Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		currentdate=new Date();
			if(dateuserentered.after(currentdate))
			{
				user.setBirthDate(null);
			}
			else user.setBirthDate(dateuserentered);
		}
		}}else user.setBirthDate(null);
		if(userimage!=null&&userimage.getContentType()!=null) 
		{
			if(!userimage.getContentType().equals("image/jpeg")&&!userimage.getContentType().equals("image/png")) 
			return "Image file is not suitable to the format. Please load a jpeg or png file";
			else user.setUserImage(userimage.getBytes());
		}
		
		String result=userService.saveUser(user);
		return result;
		
		
	}
	@PostMapping("/enteruser")
	public User enterUser(String username,String password) 
	{
		User user=userService.getOneUserByUsername(username);
		if(user==null) return null;
		boolean result= userService.enterUser(username,password);
		if(result==true) return user;
		else return null;
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
	public String changeUserPassword(long userId, String newpassword)
	{
		User user=userService.getOneUserById(userId);
		if(!newpassword.matches("^[öüÖÜĞğşŞçÇıİa-zA-Z0-9]{2,20}$")) return "New password is not suitable to the format.";
		if(user!=null)
		{
			user.setPassword(newpassword);
			userService.saveUser(user);
			return "Password change is successful";
		}else return "User not found";
	}
	

}
