package com.demo.webapideneme1.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.webapideneme1.models.Message;
import com.demo.webapideneme1.services.MessageService;

@CrossOrigin
@RestController
@RequestMapping("/messages")
public class MessageController {
	
	MessageService messageService;
	
	@Autowired
	public MessageController(MessageService messageService) {
		super();
		this.messageService = messageService;
	}
	
	@GetMapping("/getonemessagebyid")
	public  Message getOneMessageById(Long messageId)
	{
		return messageService.getOneMessageById(messageId);
	}
	@PostMapping("/createmessage")
	public String createMessage(Long messageSenderId,Long messageReceiverId,
			String messageContent,Long quotedMessageId)
	{
		
		return messageService.createMessage(messageSenderId,messageReceiverId,
				messageContent,quotedMessageId);
	}
	@PostMapping("/editmessagecontent")
	public String editMessageContent(Long messageId,
			String newMessageContent)
	{
		return messageService.editMessageContent(messageId,newMessageContent);
	}
	
	@PostMapping("/editmessagestatus")
	public String editMessageContent(Long messageId,
			boolean newMessageStatus)
	{
		return messageService.editMessageStatus(messageId,newMessageStatus);
	}
	
	@PostMapping("/editquotedmessageofamessage")
	public String editQuotedMessageOfAMessage(Long messageId,
			Long newQuotedMessageId)
	{
		return messageService.editQuotedMessageOfAMessage(messageId,newQuotedMessageId);
	}
	
	@PostMapping("/deletemessage")
	public String deleteMessage(Long messageId,Long messageSenderId)
	{
		return messageService.deleteMessage(messageId);
	}
	
	@GetMapping("/getmessagesbetweentwousers")
	public List<Message> getMessagesBetweenTwoUsers(Long user1Id,Long user2Id)
	{
		return messageService.getMessagesBetweenTwoUsers(user1Id,user2Id);
	}
	

}
