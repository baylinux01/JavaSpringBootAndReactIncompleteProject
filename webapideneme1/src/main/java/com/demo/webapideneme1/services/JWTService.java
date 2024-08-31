package com.demo.webapideneme1.services;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
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
	//@Value("${secret}")
	//private static String SECRET;
	private static SecretKey sk;
	
	
	@Bean
	private static SecretKey generateSecretKey() throws NoSuchAlgorithmException {
		String secretKey="";
		KeyGenerator keyGen=KeyGenerator.getInstance("HmacSHA256");
		//SecretKey 
		sk=keyGen.generateKey();
		return sk;
		
//		byte[] decodedKey=Base64.getDecoder().decode(SECRET);
//		return Keys.hmacShaKeyFor(decodedKey);
	
	}
	
//	private static Key getKey() throws NoSuchAlgorithmException {
//		String secretKey="";
//		KeyGenerator keyGen=KeyGenerator.getInstance("HmacSHA256");
//		SecretKey sk=keyGen.generateKey();
//		secretKey=Base64.getEncoder().encodeToString(sk.getEncoded());
//		byte[] keyBytes=Decoders.BASE64.decode(secretKey);
//		return Keys.hmacShaKeyFor(keyBytes);
//	
//	}
	
	public String generateToken(String username) throws InvalidKeyException, NoSuchAlgorithmException {
		
		
		Map<String,Object> claims=new HashMap();
		
		return //"Bearer "+
				Jwts
				.builder()
				.claims()
				.add(claims)
				.subject(username)
				.issuedAt(new Date(new Date().getTime()))
				.expiration(new Date(System.currentTimeMillis()+EXPIRES_IN))
				.and()
				.signWith(sk)//generateSecretKey())
				.compact();
	}
	
	private Claims getClaims(String jwt) throws JwtException, IllegalArgumentException, NoSuchAlgorithmException
	{
		return Jwts.parser()
				.verifyWith(sk)
				.build()
				.parseSignedClaims(jwt)
				.getPayload();
	}
	
	public String extractUsername(String jwt) throws JwtException, IllegalArgumentException, NoSuchAlgorithmException {
		
		Claims claims=getClaims(jwt);
		return claims.getSubject();
	}

	public boolean isTokenValid(String jwt, UserDetails userDetails) throws JwtException, IllegalArgumentException, NoSuchAlgorithmException {
		
		Claims claims=getClaims(jwt);
		return claims.getExpiration().after(Date.from(Instant.now()));
		
	}
	

}
