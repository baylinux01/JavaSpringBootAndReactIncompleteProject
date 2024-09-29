package com.demo.webapideneme1.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.webapideneme1.models.ConnectionRequest;
import com.demo.webapideneme1.models.User;
import com.demo.webapideneme1.services.ConnectionRequestService;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
@RequestMapping("/connectionrequests")
public class ConnectionRequestController {
 
	ConnectionRequestService connectionRequestService;
	
	@Autowired
	public ConnectionRequestController(ConnectionRequestService connectionRequestService) {
		super();
		this.connectionRequestService = connectionRequestService;
	}
	
	@GetMapping("/getallconnectionrequests")
	protected List<ConnectionRequest> getAllConnectionRequests()
	{
		List<ConnectionRequest> allConnectionRequests=
				connectionRequestService.getAllConnectionRequests();
		return allConnectionRequests;
	}
	
	@PostMapping("/createconnectionrequest")
	protected String createConnectionRequest
	(HttpServletRequest request,Long connectionRequestReceiverId)
	{
		String result=connectionRequestService
				.createConnectionRequest(request,connectionRequestReceiverId);
		return result;
	
	}
	
	
 
}
