package com.none.no_name.auth.jwt.filter;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.none.no_name.auth.util.AuthConstant;
import com.none.no_name.auth.dto.JwtLogin;
import com.none.no_name.auth.jwt.util.JwtProvider;

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
			// POST 메서드로만 로그인 요청을 받고 싶은 경우
			// 다른 메서드로 들어온 요청일 시 예외를 발생
		}

		try {
			JwtLogin loginDto = getLoginDtoFrom(request);

			UsernamePasswordAuthenticationToken authenticationToken =
				new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());

			return authenticationManager.authenticate(authenticationToken);

		}  catch (Exception exception) {
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
