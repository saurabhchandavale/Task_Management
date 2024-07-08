package com.example.demo.config;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtProvider {

	static SecretKey key = Keys.hmacShaKeyFor(JwtContant.SECRET_KEY.getBytes());
	
	public static String generateToken(Authentication auth) {
	Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
	
	String role = populateAuthorities(authorities);
	
	String jwt = Jwts.builder()
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime()+ 86400000))
				.claim("email", auth.getName())
				.claim("authorities", role)
				.signWith(key)
				.compact();
	
	return jwt;
	}

	private static String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
		// TODO Auto-generated method stub
		Set<String> auths = new HashSet<>();
		for(GrantedAuthority authority : authorities) {
			auths.add(authority.getAuthority());
		}
		return String.join(",", auths);
	}
	
	public static String getEmailFromJwtToken(String jwt) {
		jwt = jwt.substring(7);
		
		Claims claims = Jwts.parser().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
		String email = String.valueOf(claims.get("email"));
		return email;
	}
	

}
