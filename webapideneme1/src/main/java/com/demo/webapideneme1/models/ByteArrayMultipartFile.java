package com.demo.webapideneme1.models;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public class ByteArrayMultipartFile implements MultipartFile{
	private  String name;
	private  String originalFileName;
	private String contentType;
	private byte[] content;
	
	
	public ByteArrayMultipartFile(String name, String originalFileName, String contentType, byte[] content) {
		super();
		this.name = name;
		this.originalFileName = originalFileName;
		this.contentType = contentType;
		this.content = content;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOriginalFileName() {
		return originalFileName;
	}
	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public byte[] getContent() {
		return content;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}
	@Override
	public String getOriginalFilename() {
		// TODO Auto-generated method stub
		return originalFileName;
	}
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return content.length==0;
	}
	@Override
	public long getSize() {
		// TODO Auto-generated method stub
		return content.length;
	}
	@Override
	public byte[] getBytes() throws IOException {
		// TODO Auto-generated method stub
		return content;
	}
	@Override
	public InputStream getInputStream() throws IOException {
		// TODO Auto-generated method stub
		return new ByteArrayInputStream(content);
	}
	@Override
	public void transferTo(File dest) throws IOException, IllegalStateException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Bu method desteklenmiyor");
	}
	
	
	
}
