package com.example.demo.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtTokenValidator extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String jwt = request.getHeader(JwtContant.JWT_HEADER);
		if(jwt!=null) {
			jwt = jwt.substring(7);
			
			try {
				System.out.println("Before key");
				SecretKey key = Keys.hmacShaKeyFor(JwtContant.SECRET_KEY.getBytes(StandardCharsets.UTF_8));
				System.out.println("key "+ key);
				Claims claims = Jwts.parser().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
				 //Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(jwt).getBody();
				System.out.println("claims "+ claims);

				String email = String.valueOf(claims.get("email"));
				String authorities = String.valueOf(claims.get("authorities"));
				
				List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
				Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, auths);
				
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
			}catch(Exception e) {
				throw new BadCredentialsException("Invalid Token .........");
			}
		
		}
		
		
		filterChain.doFilter(request, response);
	}

}
