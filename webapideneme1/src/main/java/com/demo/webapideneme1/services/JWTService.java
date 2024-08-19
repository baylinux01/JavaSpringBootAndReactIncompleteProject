package com.demo.webapideneme1.services;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {

//	@Value("${app.secret}")
//	private String APP_SECRET;

	@Value("${expires.in}")
	private Long EXPIRES_IN;
	
	private static Key getKey() throws NoSuchAlgorithmException {
		String secretKey="";
		KeyGenerator keyGen=KeyGenerator.getInstance("HmacSHA256");
		SecretKey sk=keyGen.generateKey();
		secretKey=Base64.getEncoder().encodeToString(sk.getEncoded());
		byte[] keyBytes=Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	
	}
	
	public String generateToken(String username) throws InvalidKeyException, NoSuchAlgorithmException {
		
		
		Map<String,Object> claims=new HashMap();
		
		return "Bearer "+Jwts
				.builder()
				.claims()
				.add(claims)
				.subject(username)
				.issuedAt(new Date(new Date().getTime()))
				.expiration(new Date(System.currentTimeMillis()+EXPIRES_IN))
				.and()
				.signWith(getKey())
				.compact();
	}
	
	
	public String extractUsername(String token) {
		
		return null;
	}

	public boolean validateToken(String token, UserDetails userDetails) {
		
		return false;
		
	}
	private Claims extractAllClaims(String token) 
	{
		return null;
	}

}
