package com.none.no_name.auth.jwt.filter;

import static com.none.no_name.auth.util.AuthUtil.*;

import java.io.IOException;

import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.web.filter.OncePerRequestFilter;

import com.none.no_name.auth.util.AuthConstant;
import com.none.no_name.auth.jwt.util.JwtProvider;
import com.none.no_name.global.exception.business.common.MethodNotAllowedException;
import com.none.no_name.global.exception.business.jwt.JwtExpiredException;
import com.none.no_name.global.exception.business.jwt.JwtNotFoundException;
import com.none.no_name.global.exception.business.jwt.JwtNotValidException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtRefreshFilter extends OncePerRequestFilter {

	private final JwtProvider jwtProvider;

	public JwtRefreshFilter(JwtProvider jwtProvider) {
		this.jwtProvider = jwtProvider;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws IOException {

		if(!request.getMethod().equals("POST")){
			throw new MethodNotAllowedException("POST");
		}
		else{
			try {
				String refreshToken = getRefreshToken(request);

				jwtProvider.validateToken(refreshToken);

				String refilledAccessToken =
					jwtProvider.refillAccessToken(refreshToken, AuthConstant.ACCESS_TOKEN_EXPIRE_TIME);

				response.setHeader(AuthConstant.AUTHORIZATION, AuthConstant.BEARER + refilledAccessToken);
			} catch (JwtExpiredException | JwtNotValidException e) {
				setResponse(response, e);
			} catch (Exception exception) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "알 수 없는 오류입니다. 다시 시도해주세요.");
			}
		}
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {

		return !request.getRequestURI().equals(AuthConstant.REFRESH_URL);
	}

	private String getRefreshToken(HttpServletRequest request) {

		String refreshToken = request.getHeader(AuthConstant.REFRESH);

		if (refreshToken == null) {
			throw new JwtNotFoundException();
		}

		return refreshToken.replace(AuthConstant.BEARER, "");
	}
}
