package com.demo.webapideneme1.services;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {

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
		
		return Jwts
				.builder()
				.claims()
				.add(claims)
				.subject(username)
				.issuedAt(new Date(new Date().getTime()))
				.expiration(new Date(System.currentTimeMillis()+60*60*30))
				.and()
				.signWith(getKey())
				.compact();
	}
	

}
