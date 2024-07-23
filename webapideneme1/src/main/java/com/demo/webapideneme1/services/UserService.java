package com.demo.webapideneme1.services;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.text.html.HTMLDocument.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.demo.webapideneme1.models.ConnectionRequest;
import com.demo.webapideneme1.models.User;
import com.demo.webapideneme1.repositories.UserRepository;


@Service
public class UserService {
	
	UserRepository userRepository;
	ConnectionRequestService connectionRequestService;
	
	
	@Autowired
	public UserService(UserRepository userRepository, ConnectionRequestService connectionRequestService) {
		super();
		this.userRepository = userRepository;
		this.connectionRequestService = connectionRequestService;
	}

	public String saveUser(User user) {
		if(user.getName()==null||user.getName().equals("")) return "Registration is unsuccessful name cannot be null";
		if(user.getSurname()==null||user.getSurname().equals("")) return "Registration is unsuccessful surname cannot be null";
		if(user.getUsername()==null||user.getUsername().equals("")) return "Registration is unsuccessful username cannot be null";
		if(user.getPassword()==null||user.getPassword().equals("")) return "Registration is unsuccessful password cannot be null";
		if(!user.getName().matches("^[öüÖÜĞğşŞçÇıİ|a-z|A-Z]{2,20}(\\s[öüÖÜĞğşŞçÇıİ|a-z|A-Z]{2,20})?$")) return "Name is not suitable to the format. Registration is unsuccessful";
		if(!user.getSurname().matches("^[öüÖÜĞğşŞçÇıİa-zA-Z]{2,20}$")) return "Surnam)e is not suitable to the format. Registration is unsuccessful";
		if(!user.getUsername().matches("^[öüÖÜĞğşŞçÇıİa-zA-Z0-9]{2,20}$")) return "Username is not suitable to the format. Registration is unsuccessful";
		if(!user.getPassword().matches("^[öüÖÜĞğşŞçÇıİa-zA-Z0-9]{2,20}$")) return "Password is not suitable to the format. Registration is unsuccessful";
		
		
		List<User> users= userRepository.findAll();
		
		for(User u : users)
		{
			if(user.getUsername().equals(u.getUsername())) return "Username cannot be the same as another one. Registration is unsuccessful";
		}
		
		userRepository.save(user);
		return "Registration is successful";
		
	}

	public String updateUser
	(long id, String newname, String newsurname, String newusername,
			MultipartFile newuserimage,String newbirthdate) 
	throws IOException 
	{
		Optional<User> user=userRepository.findById(id);
		List<User> users;
		String newname2="";
		String newsurname2="";
		String newusername2="";
		byte[] newuserimage2=null;
		Date newbirthdate2;
		if(user.isPresent())
		{
		
		if(newname==null ||newname.equals(""))newname2=user.get().getName();
		else newname2=newname;
		if(newsurname==null||newsurname.equals(""))newsurname2=user.get().getSurname();
		else newsurname2=newsurname;
		if(newusername==null||newusername.equals(""))newusername2=user.get().getUsername();
		else newusername2=newusername;
		if(newbirthdate==null || newbirthdate.equals("")) newbirthdate2=user.get().getBirthDate();
		else
		{
			int day=0;
			int month=0;
			int year=0000;
			if(newbirthdate!=null)
			{
				String[] arr=new String[3];
				arr=newbirthdate.split("-");
				year=Integer.valueOf(arr[0]);
				month=Integer.valueOf(arr[1]);
				day=Integer.valueOf(arr[2]);
				
			}
			LocalDate localDate=LocalDate.of(year, month, day);
			newbirthdate2 = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		}
		
		if(!newname2.matches("^[öüÖÜĞğşŞçÇıİ|a-z|A-Z]{2,20}(\\s[öüÖÜĞğşŞçÇıİ|a-z|A-Z]{2,20})?$")) return "Name is not suitable to the format. Update is unsuccessful";
		if(!newsurname2.matches("^[öüÖÜĞğşŞçÇıİa-zA-Z]{2,20}$")) return "Surname is not suitable to the format. Update is unsuccessful";
		if(!newusername2.matches("^[öüÖÜĞğşŞçÇıİa-zA-Z0-9]{2,20}$")) return "Username is not suitable to the format. Update is unsuccessful";
		if(newuserimage!=null&&newuserimage.getContentType()!=null) 
		{
			if(!newuserimage.getContentType().equals("image/jpeg")&&!newuserimage.getContentType().equals("image/png"))
				return "Image file is not suitable to the format. Please load a jpeg or png file";
		}
		
		if(newuserimage==null||newuserimage.getContentType()==null
			||(!newuserimage.getContentType().equals("image/jpeg")
			&&!newuserimage.getContentType().equals("image/png"))) 
			newuserimage2=user.get().getUserImage();
		else newuserimage2=newuserimage.getBytes();
		
		users= userRepository.findAll();
		users.remove(user.get());
		for(User u : users)
		{
			if(newusername2.equals(u.getUsername())) return "Username cannot be the same as another one. Update is unsuccessful";
		}
		}else return "User not found. Update is unsuccessful";
		user.get().setName(newname2);
		user.get().setSurname(newsurname2);
		user.get().setUsername(newusername2);
		user.get().setUserImage(newuserimage2);
		user.get().setBirthDate(newbirthdate2);
		userRepository.save(user.get());
		return "Update is successful";
	}

	public List<User> getAllUsers() {
		List<User> users=userRepository.findAll();
		
		return users;
		
	}

	public String deleteUser(long id) {
		Optional<User> user=userRepository.findById(id);
		if(user.isPresent()) userRepository.delete(user.get());
		else return "User not found. User deletion is unsuccessful";
		return "User deletion is successful";
	}

	public User getOneUserById(Long userId) {
		User user=userRepository.findById(userId).orElse(null);
		if(user!=null)
		{
			
			return user;
		}
		return null;
	}

	public User getOneUserByUsername(String username) {
		User user= userRepository.findByUsername(username);
		return user;
	}

	public boolean enterUser(String username, String password) {
		List<User> users= userRepository.findAll();
		for(User u : users)
		{
			if( u.getUsername().equals(username) && u.getPassword().equals(password))
			{
				return true;
			}
		}
		return false;
	}

	public List<User> getSearchedUsers(String searchedWords) {
		String[] array= searchedWords.split("\\s");
		int i=0;
		Set<User> searchedUsersSet=new HashSet<User>();
		List<User> prevUsers=new ArrayList<User>();
		while(i<array.length)
		{
			List<User> users=new ArrayList<User>();
			users=userRepository.getSearchedUsers(array[i]);
			
				
				int b=0;
				while(b<users.size())
				{
					if(!searchedUsersSet.contains(users.get(b)))
					searchedUsersSet.clear();
					b++;
					if(searchedUsersSet.size()>users.size())
					searchedUsersSet.clear();
					
				}
			
			
			
				int d=0;
				while(d<users.size())
				{
					if(prevUsers.size()>0 && !prevUsers.contains(users.get(d)))
					{
						users.remove(users.get(d));
						d--;
					}
					d++;
				}
			prevUsers.clear();
			prevUsers.addAll(users);
			searchedUsersSet.addAll(users);
			i++;
		}
		List<User> searchedUsersList=new ArrayList<User>(searchedUsersSet);
		Comparator<User> myComparator=Comparator.comparing(User::getName,String.CASE_INSENSITIVE_ORDER)
				.thenComparing(User::getSurname,String.CASE_INSENSITIVE_ORDER);
		List<User> sortedSearchedUsersList=searchedUsersList.stream()
				.sorted(myComparator).collect(Collectors.toList());
		return sortedSearchedUsersList;
	}

	public List<User> getBannedUsersOfAUser(Long userId) {
		User user=userRepository.findById(userId).orElse(null);
		if(user!=null&&user.getBannedUsers()!=null)
		return user.getBannedUsers();
		else return null;
	}

	public String banUser(Long banningUserId, Long userToBeBannedId) {
		
		User banningUser=userRepository.findById(banningUserId).orElse(null);
		User userToBeBanned=userRepository.findById(userToBeBannedId).orElse(null);
		if(!banningUser.getBannedUsers().contains(userToBeBanned))
		{
			if(banningUser.getConnections().contains(userToBeBanned))
				banningUser.getConnections().remove(userToBeBanned);
			if(userToBeBanned.getConnections().contains(banningUser))
				userToBeBanned.getConnections().remove(banningUser);
			List<ConnectionRequest> conreqs=
					connectionRequestService.getAllConnectionRequests();
			if(conreqs!=null&&conreqs.size()>0)
			{
				int i=0;
				while(i<conreqs.size())
				{
					if((conreqs.get(i).getConnectionRequestSender()==
							userToBeBanned && conreqs.get(i).getConnectionRequestReceiver()==banningUser)
							||
							(conreqs.get(i).getConnectionRequestReceiver()==
							userToBeBanned && conreqs.get(i).getConnectionRequestSender()==banningUser))
						{
							conreqs.remove(conreqs.get(i));
							i--;
						}
							i++;
				}
			}
			banningUser.getBannedUsers().add(userToBeBanned);
			connectionRequestService.saveAll(conreqs);
			userRepository.save(banningUser);
			userRepository.save(userToBeBanned);
			return "success";
			
			
		}
		
		return "false";
	}

	

	
	

}
