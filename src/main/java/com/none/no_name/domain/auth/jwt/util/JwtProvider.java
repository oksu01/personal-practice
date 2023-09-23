package com.none.no_name.domain.auth.jwt.util;

import static com.none.no_name.domain.auth.util.AuthConstant.*;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;

import com.none.no_name.domain.auth.jwt.userdetail.CustomUserDetails;
import com.none.no_name.domain.auth.jwt.userdetail.CustomUserDetailsService;
import com.none.no_name.global.exception.business.jwt.JwtExpiredException;
import com.none.no_name.global.exception.business.jwt.JwtNotValidException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import jakarta.annotation.PostConstruct;

@Component
public class JwtProvider {
	@Value("${jwt.key}")
	private String key;

	private Key secretKey;

	private CustomUserDetailsService userDetailsService;

	public JwtProvider(CustomUserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@PostConstruct
	protected void init() {
		secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));
	}

	public String createAccessToken(Authentication authentication, Long tokenExpireTime) {

		return Jwts.builder()
			.setSubject(authentication.getName())
			.claim(CLAIM_ID, getId(authentication))
			.claim(CLAIM_AUTHORITY, getAuthorities(authentication))
			.setExpiration(getExpiration(tokenExpireTime))
			.signWith(secretKey, SignatureAlgorithm.HS512)
			.compact();
	}

	public String createRefreshToken(Authentication authentication, long tokenExpireTime){

		return Jwts.builder()
			.setSubject(authentication.getName())
			.claim(CLAIM_ID, getId(authentication))
			.setExpiration(getExpiration(tokenExpireTime))
			.signWith(secretKey, SignatureAlgorithm.HS512)
			.compact();
	}

	public String refillAccessToken(String refreshToken, long tokenExpireTime) {
		Claims claims = getClaims(refreshToken);
		String username = claims.getSubject();

		UserDetails userDetails = userDetailsService.loadUserByUsername(username);

		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
			userDetails, null, userDetails.getAuthorities());

		return createAccessToken(authentication, tokenExpireTime);
	}

	private Long getId(Authentication authentication) {

		if(authentication.getPrincipal() instanceof CustomUserDetails customUserDetails){
			return customUserDetails.getMember().getMemberId();
		}

		if(authentication.getPrincipal() instanceof DefaultOAuth2User principal){
			return principal.getAttribute(CLAIM_ID);
		}

		return null;
	}

	private String getAuthorities(Authentication authentication) {

		return authentication.getAuthorities().stream()
			.map(GrantedAuthority::getAuthority)
			.collect(Collectors.joining(","));
	}

	private Date getExpiration(Long tokenExpireTime) {
		return new Date(new Date().getTime() + tokenExpireTime);
	}

	public Claims getClaims(String token) {
		try {
			return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
		} catch (ExpiredJwtException e) {
			throw new JwtExpiredException();
		} catch (SecurityException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
			throw new JwtNotValidException();
		}
	}

	public void validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
		} catch (ExpiredJwtException e) {
			throw new JwtExpiredException();
		} catch (SecurityException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
			throw new JwtNotValidException();
		}
	}
}
