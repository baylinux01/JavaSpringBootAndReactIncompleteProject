package com.demo.webapideneme1.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id",scope=Comment.class)
@Entity
public class Comment {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@ManyToOne(fetch=FetchType.EAGER)
	private User owner;
	private String content;
	@ManyToOne
	private Comment quotedComment;
//	@OneToMany
//	private List<Comment> quotingComments;
	@ManyToOne
	private Group group;
	
	public Comment() {
		super();
		// TODO Auto-generated constructor stub
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Comment getQuotedComment() {
		return quotedComment;
	}
	public void setQuotedComment(Comment quotedComment) {
		this.quotedComment = quotedComment;
	}
//	public List<Comment> getQuotingComments() {
//		return quotingComments;
//	}
//	public void setQuotingComments(List<Comment> quotingComments) {
//		this.quotingComments = quotingComments;
//	}
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
	}
	
	
	
}
