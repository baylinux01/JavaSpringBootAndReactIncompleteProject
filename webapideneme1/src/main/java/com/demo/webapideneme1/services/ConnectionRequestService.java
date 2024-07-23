package com.demo.webapideneme1.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.webapideneme1.models.ConnectionRequest;
import com.demo.webapideneme1.models.User;
import com.demo.webapideneme1.repositories.ConnectionRequestRepository;
import com.demo.webapideneme1.repositories.UserRepository;

@Service
public class ConnectionRequestService {
 
	ConnectionRequestRepository connectionRequestRepository;
	UserRepository userRepository;
	
	@Autowired
	public ConnectionRequestService(ConnectionRequestRepository connectionRequestRepository, UserRepository userRepository) {
		super();
		this.connectionRequestRepository = connectionRequestRepository;
		this.userRepository = userRepository;
	}
	
	public List<ConnectionRequest> getAllConnectionRequests() {
		// TODO Auto-generated method stub
		return connectionRequestRepository.findAll();
	}
	
	public String createConnectionRequest(Long connectionRequestSenderId, Long connectionRequestReceiverId) {
		
		List<ConnectionRequest> allConReqs=connectionRequestRepository.findAll();
		User connectionRequestSender=userRepository.findById(connectionRequestSenderId).orElse(null);
		User connectionRequestReceiver=userRepository.findById(connectionRequestReceiverId).orElse(null);
		if(connectionRequestReceiver.getBannedUsers().contains(connectionRequestSender)
				||connectionRequestSender.getBannedUsers().contains(connectionRequestReceiver))
			return "conreq cannot be created because of a ban";
		if(allConReqs!=null&&allConReqs.size()>0)
		{
			for(ConnectionRequest conReq:allConReqs)
			{
				if((conReq.getConnectionRequestSender()==connectionRequestSender
						&&conReq.getConnectionRequestReceiver()==connectionRequestReceiver)
						||(conReq.getConnectionRequestSender()==connectionRequestReceiver
								&&conReq.getConnectionRequestReceiver()==connectionRequestSender))
				{
					return "conreq already exists";
				}
			}
		}
			ConnectionRequest cRequest=new ConnectionRequest();
			cRequest.setConnectionRequestSender(connectionRequestSender);
			cRequest.setConnectionRequestReceiver(connectionRequestReceiver);
			try {
			connectionRequestRepository.save(cRequest);
			return "conreq succesffully saved";
			}catch(Exception ex){
				return ex.getMessage();
			}
	}

	public void saveAll(List<ConnectionRequest> conreqs) {
		connectionRequestRepository.deleteAll();
		connectionRequestRepository.saveAll(conreqs);
		
	}
	
	
 
}
