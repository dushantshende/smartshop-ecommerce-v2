package com.ds.smartshop.security.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ds.smartshop.security.UserServiceDetails;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	private final JwtService jwtService;
	private final UserServiceDetails userServiceDetails;
	
	public JwtAuthenticationFilter(JwtService jwtService,UserServiceDetails userServiceDetails) {
		this.jwtService=jwtService;
		this.userServiceDetails=userServiceDetails;
	}
	
	
	
	@Override
	protected void doFilterInternal(
			HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain)
					throws ServletException, IOException {
		
		final String authHeader=request.getHeader("Authorization");
		
		if(authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		
		final String jwtToken=authHeader.substring(7);
		
		try{
			String email= jwtService.extractEmail(jwtToken);
			
			
			if(SecurityContextHolder.getContext().getAuthentication()==null) {
				UserDetails userDetails=userServiceDetails.loadUserByUsername(email);
				
				
				if(jwtService.isTokenValid(jwtToken, userDetails.getUsername())) {
					UsernamePasswordAuthenticationToken authentication=
							new UsernamePasswordAuthenticationToken(
									userDetails, null,userDetails.getAuthorities());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authentication);
					
				}
			}
		}catch (Exception e) {
			
			filterChain.doFilter(request, response);
			return ;
			}
		filterChain.doFilter(request, response);
		
	}
	
	

		


}
