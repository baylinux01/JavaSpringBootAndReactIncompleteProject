package com.demo.webapideneme1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.demo.webapideneme1.models.MyUserDetails;
import com.demo.webapideneme1.models.User;

@Service
public class MyUserDetailsService implements UserDetailsService {

	private UserService userService;
	
	@Autowired
	public MyUserDetailsService(UserService userService) {
		super();
		this.userService = userService;
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user= userService.getOneUserByUsername(username);
		if(user==null)
		{
			System.out.println("user not found");
			throw new UsernameNotFoundException("User not found");
		}
		return new MyUserDetails(user);
	}

}
