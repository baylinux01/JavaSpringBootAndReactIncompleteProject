package com.demo.webapideneme1.models;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
@JsonIdentityInfo
(generator=ObjectIdGenerators.PropertyGenerator.class, property="id",scope=User.class)
@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private String name;
	private String surname;
	private String username;
	@JsonIgnore
	//@JsonProperty(access=Access.WRITE_ONLY)
	private String password;
	@Lob
	private byte[] userImage;
	private Date birthDate;
	@JsonIgnore
	@ManyToMany
	private List<User> bannedUsers;
	@JsonIgnore
	@ManyToMany
	private List<User> connections;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public byte[] getUserImage() {
		return userImage;
	}
	public void setUserImage(byte[] userImage) {
		this.userImage = userImage;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public List<User> getBannedUsers() {
		return bannedUsers;
	}
	public void setBannedUsers(List<User> bannedUsers) {
		this.bannedUsers = bannedUsers;
	}
	public List<User> getConnections() {
		return connections;
	}
	public void setConnections(List<User> connections) {
		this.connections = connections;
	}
	
	

}
	
	
	
	
	
	
	
	
	
	
	


