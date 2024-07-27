package com.demo.webapideneme1.models;



import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@JsonIdentityInfo
(generator=ObjectIdGenerators.PropertyGenerator.class, property="id",scope=ConnectionRequest.class)
@Entity
public class ConnectionRequest {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@ManyToOne
	private User connectionRequestSender;
	@ManyToOne
	private User connectionRequestReceiver;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public User getConnectionRequestSender() {
		return connectionRequestSender;
	}
	public void setConnectionRequestSender(User connectionRequestSender) {
		this.connectionRequestSender = connectionRequestSender;
	}
	public User getConnectionRequestReceiver() {
		return connectionRequestReceiver;
	}
	public void setConnectionRequestReceiver(User connectionRequestReceiver) {
		this.connectionRequestReceiver = connectionRequestReceiver;
	}
	
	
}
