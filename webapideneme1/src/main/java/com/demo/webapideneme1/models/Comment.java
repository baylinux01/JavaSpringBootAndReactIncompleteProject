package com.demo.webapideneme1.models;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;


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
	//@JsonFormat(shape=JsonFormat.Shape.STRING,pattern="dd-MM-yyyy")
	private Date commentDate=new Date();
	//@JsonFormat(shape=JsonFormat.Shape.STRING,pattern="dd-MM-yyyy")
	private Date commentEditDate=new Date();
	
	
	
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



	public Group getGroup() {
		return group;
	}



	public void setGroup(Group group) {
		this.group = group;
	}



	public Date getCommentDate() {
		return commentDate;
	}



	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}



	public Date getCommentEditDate() {
		return commentEditDate;
	}



	public void setCommentEditDate(Date commentEditDate) {
		this.commentEditDate = commentEditDate;
	}
	
	
	
	
}
