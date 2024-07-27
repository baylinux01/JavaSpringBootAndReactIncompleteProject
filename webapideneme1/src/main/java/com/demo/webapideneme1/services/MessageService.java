package com.demo.webapideneme1.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.webapideneme1.models.Message;
import com.demo.webapideneme1.models.User;
import com.demo.webapideneme1.repositories.MessageRepository;

@Service
public class MessageService {
	UserService userService;
	MessageRepository messageRepository;
	
	@Autowired
	public MessageService(UserService userService, MessageRepository messageRepository) {
		super();
		this.userService = userService;
		this.messageRepository = messageRepository;
	}

	public Message getOneMessageById(Long messageId) {
		
		return messageRepository.findById(messageId).orElse(null);
	}

	public String createMessage(Long messageSenderId, Long messageReceiverId, 
			String messageContent, Long quotedMessageId) 
	{
		User messageSender=userService.getOneUserById(messageSenderId);
		User messageReceiver=userService.getOneUserById(messageReceiverId);
		Message quotedMessage=null;
		if(quotedMessageId!=null)
		{
			 quotedMessage=messageRepository
				.findById(quotedMessageId).orElse(null);
		}
		if(!messageReceiver.getBannedUsers().contains(messageSender)
				&&!messageSender.getBannedUsers().contains(messageReceiver)
				&&messageSender.getConnections().contains(messageReceiver)
				&&messageReceiver.getConnections().contains(messageSender))
		{
			Message message=new Message();
			message.setMessageSender(messageSender);
			message.setMessageReceiver(messageReceiver);
			if(quotedMessage!=null)
			message.setQuotedMessage(quotedMessage);
			message.setMessageContent(messageContent);
			messageRepository.save(message);
			return "success";
			
		}else
		return "fail";
	}

	public String editMessageContent(Long messageId, String newMessageContent) {
		Message message=messageRepository.findById(messageId).orElse(null);
		if(message!=null)
		{
			message.setMessageContent(newMessageContent);
			message.setMessageEditDate(new Date());
			messageRepository.save(message);
			return "success";
		}
		return "fail";
	}

	public String editQuotedMessageOfAMessage(Long messageId, Long newQuotedMessageId) {
		Message message=messageRepository.findById(messageId).orElse(null);
		Message newQuotedMessage=messageRepository.findById(newQuotedMessageId).orElse(null);
		if(message!=null)
		{
			message.setQuotedMessage(newQuotedMessage);
			message.setMessageEditDate(new Date());
			messageRepository.save(message);
			return "success";
		}
		return "fail";
	}

	public String deleteMessage(Long messageId) {
		Message message=messageRepository.findById(messageId).orElse(null);
		
		if(message!=null)
		{
			messageRepository.delete(message);
			return "message deleted";
		}
			return "fail";
		
		
	}

	public List<Message> getMessagesBetweenTwoUsers(Long user1Id, Long user2Id) {
		User user1=userService.getOneUserById(user1Id);
		User user2=userService.getOneUserById(user2Id);
		List<Message> messages=null;
		if(user1!=null && user2!=null)
		{
			messages=messageRepository
					.findByMessageSenderandMessageReceiver(user1Id,user2Id);
			return messages;
		}else
		
		return null;
	}

	public String editMessageStatus(Long messageId, boolean newMessageStatus) {
		Message message=messageRepository.findById(messageId).orElse(null);
		if(message!=null)
		{
			message.setRead(newMessageStatus);
			messageRepository.save(message);
			return "success";
		}
		
		return "fail";
	}

	

	
	
	

}
