package com.demo.webapideneme1.filters;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.demo.webapideneme1.services.JWTService;
import com.demo.webapideneme1.services.MyUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class JWTFilter extends OncePerRequestFilter{

	private JWTService jWTService;
	private ApplicationContext applicationContext;
	private MyUserDetailsService myUserDetailsService;
	
	
	
	@Autowired
	public JWTFilter(JWTService jWTService,MyUserDetailsService myUserDetailsService,ApplicationContext applicationContext) {
		super();
		this.jWTService = jWTService;
		this.applicationContext=applicationContext;
		this.myUserDetailsService=myUserDetailsService;
	}



	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String authHeader=request.getHeader("Authorization");
		String token=null;
		String username=null;
		if(authHeader==null || !authHeader.startsWith("Bearer "))
			{
				filterChain.doFilter(request, response);
				return;
			}
		if(authHeader!=null && authHeader.startsWith("Bearer "))
		{
			//token=authHeader.substring("Bearer ".length());
			token=authHeader.substring(7);
			username=jWTService.extractUsername(token);
		}
		
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
		{
			//UserDetails userDetails=applicationContext.getBean(MyUserDetailsService.class).loadUserByUsername(username);
			UserDetails userDetails=myUserDetailsService.loadUserByUsername(username);
			if(jWTService.validateToken(token,userDetails))
			{
				UsernamePasswordAuthenticationToken authToken= 
						new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		filterChain.doFilter(request,response);
	}

}
