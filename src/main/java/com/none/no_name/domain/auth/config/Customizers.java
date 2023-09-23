package com.none.no_name.domain.auth.config;

import static com.none.no_name.domain.auth.util.AuthConstant.*;

import java.util.List;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.none.no_name.domain.auth.jwt.handler.CustomAccessDeniedHandler;
import com.none.no_name.domain.auth.jwt.handler.CustomAuthenticationEntryPoint;

public class Customizers {

	static Customizer<HeadersConfigurer<HttpSecurity>> getHeadersConfigurer() {
		return (headers) -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable);
	}

	static Customizer<CorsConfigurer<HttpSecurity>> getCorsConfigurer() {

		return cors -> {
			CorsConfiguration configuration = new CorsConfiguration();
			configuration.setAllowedOrigins(List.of("http://localhost:3000"));
			configuration.addAllowedMethod("*");
			configuration.addAllowedHeader("*");
			configuration.setAllowCredentials(true);
			configuration.setExposedHeaders(List.of(AUTHORIZATION, REFRESH, LOCATION));

			UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
			source.registerCorsConfiguration("/**", configuration);

			cors.configurationSource(source);
		};
	}

	static Customizer<SessionManagementConfigurer<HttpSecurity>> getSessionManagementConfigurer() {

		return sessionManagementConfigurer -> new SessionManagementConfigurer<>()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	static Customizer<ExceptionHandlingConfigurer<HttpSecurity>> getExceptionHandlingConfigurer() {

		return exceptionHandlingConfigurer -> new ExceptionHandlingConfigurer<>()
			.authenticationEntryPoint(getCustomAuthenticationEntryPoint())
			.accessDeniedHandler(getCustomAccessDeniedHandler());
	}

	static Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> getAuthorizeHttpRequestsConfigurer() {

		return auth -> auth
			.requestMatchers(
				new AntPathRequestMatcher("/auth/test")
			).hasRole("USER")
			.anyRequest().permitAll();
	}

	static AuthenticationEntryPoint getCustomAuthenticationEntryPoint() {
		return new CustomAuthenticationEntryPoint();
	}

	static AccessDeniedHandler getCustomAccessDeniedHandler() {
		return new CustomAccessDeniedHandler();
	}
}
