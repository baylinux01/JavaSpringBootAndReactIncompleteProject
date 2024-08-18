package com.demo.webapideneme1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.demo.webapideneme1.models.MyUserDetails;
import com.demo.webapideneme1.models.User;
import com.demo.webapideneme1.repositories.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

	private UserRepository userRepository;

	@Autowired
	public MyUserDetailsService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user= userRepository.findByUsername(username);
		if(user==null)
		{
			System.out.println("user not found");
			throw new UsernameNotFoundException("User not found");
		}
		return new MyUserDetails(user);
	}
	
	public UserDetails loadUserById(long id)
	{
		User user=userRepository.findById(id).orElse(null);
		if(user==null)
		{
			System.out.println("user not found");
			throw new UsernameNotFoundException("User not found");
		}
		return new MyUserDetails(user);
	}

}
