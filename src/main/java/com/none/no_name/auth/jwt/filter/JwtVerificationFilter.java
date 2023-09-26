package com.none.no_name.auth.jwt.filter;

import static com.none.no_name.auth.util.AuthConstant.*;
import static com.none.no_name.auth.util.AuthUtil.*;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.none.no_name.auth.jwt.userdetail.CustomUserDetails;
import com.none.no_name.auth.jwt.userdetail.CustomUserDetailsService;
import com.none.no_name.auth.util.AuthConstant;
import com.none.no_name.auth.jwt.util.JwtProvider;
import com.none.no_name.auth.util.AuthUtil;
import com.none.no_name.global.exception.business.BusinessException;
import com.none.no_name.global.exception.business.jwt.JwtExpiredException;
import com.none.no_name.global.exception.business.jwt.JwtNotValidException;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtVerificationFilter extends OncePerRequestFilter {

	private final JwtProvider jwtProvider;
	private final CustomUserDetailsService customUserDetailsService;

	public JwtVerificationFilter(JwtProvider jwtProvider, CustomUserDetailsService customUserDetailsService) {
		this.jwtProvider = jwtProvider;
		this.customUserDetailsService = customUserDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

		try{
			Claims claims = verifyClaims(request);
			setAuthenticationToContext(claims);
		} catch(BusinessException e){
			setResponse(response, e);
			return;
		} catch(Exception e){
			setResponse(response);
			return;
		}

		filterChain.doFilter(request, response);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {

		String accessToken = getAccessToken(request);

		return accessToken == null || !accessToken.startsWith(AuthConstant.BEARER);
	}

	private Claims verifyClaims(HttpServletRequest request) {

		String accessToken = getAccessToken(request).replace(AuthConstant.BEARER, "");

		return jwtProvider.getClaims(accessToken);
	}

	private String getAccessToken(HttpServletRequest request) {

		return request.getHeader(AuthConstant.AUTHORIZATION);
	}

	private void setAuthenticationToContext(Claims claims) {

		CustomUserDetails principal =
			(CustomUserDetails) customUserDetailsService.loadUserByUsername(claims.getSubject());

		Collection<? extends GrantedAuthority> authorities = principal.getAuthorities();

		Authentication authentication =
			new UsernamePasswordAuthenticationToken(principal, null, authorities);

		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
}
