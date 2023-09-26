package com.none.no_name.auth.jwt.filter;

import static com.none.no_name.auth.util.AuthUtil.*;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.none.no_name.auth.util.AuthConstant;
import com.none.no_name.auth.dto.JwtLogin;
import com.none.no_name.auth.jwt.util.JwtProvider;
import com.none.no_name.auth.util.AuthUtil;
import com.none.no_name.global.exception.business.common.MethodNotAllowedException;
import com.none.no_name.global.exception.business.member.MemberNotFoundException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final JwtProvider jwtProvider;
	private final AuthenticationManager authenticationManager;

	public JwtAuthenticationFilter(JwtProvider jwtProvider, AuthenticationManager authenticationManager1) {
		this.jwtProvider = jwtProvider;
		this.authenticationManager = authenticationManager1;
	}

	@SneakyThrows
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

		if (!request.getMethod().equals("POST")) {
			throw new MethodNotAllowedException("POST");
		}

		try {
			JwtLogin loginDto = getLoginDtoFrom(request);

			UsernamePasswordAuthenticationToken authenticationToken =
				new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());

			return authenticationManager.authenticate(authenticationToken);

		} catch (BadCredentialsException e) {
			return null;
			// 잘못된 자격 증명 처리
		} catch (LockedException e) {
			return null;
			// 계정 잠금 처리
		} catch (DisabledException e) {
			return null;
			// 계정 비활성화 처리
		} catch (AccountExpiredException e) {
			return null;
			// 계정 만료 처리
		} catch (CredentialsExpiredException e) {
			return null;
			// 자격 증명(비밀번호) 만료 처리
		} catch (UsernameNotFoundException e) {
			setResponse(response, new MemberNotFoundException());
			return null;
			// 사용자가 존재하지 않는 경우 처리
		} catch (AuthenticationServiceException e) {
			return null;
			// 다른 인증 예외 처리
		} catch (Exception e) {
			setResponse(response);
			return null;
		}
	}

	private JwtLogin getLoginDtoFrom(HttpServletRequest request) throws IOException {
		return new ObjectMapper().readValue(request.getInputStream(), JwtLogin.class);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
		FilterChain chain, Authentication authResult) throws IOException {

		String accessToken = jwtProvider.createAccessToken(authResult, AuthConstant.ACCESS_TOKEN_EXPIRE_TIME);
		String refreshToken = jwtProvider.createRefreshToken(authResult, AuthConstant.REFRESH_TOKEN_EXPIRE_TIME);

		response.setHeader(AuthConstant.AUTHORIZATION, AuthConstant.BEARER + accessToken);
		response.setHeader(AuthConstant.REFRESH, AuthConstant.BEARER + refreshToken);

		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write("로그인 성공");
	}
}
