package com.none.no_name.domain.auth.config;

import static com.none.no_name.domain.auth.config.Customizers.*;
import static com.none.no_name.domain.auth.util.AuthConstant.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.none.no_name.domain.auth.jwt.filter.JwtAuthenticationFilter;
import com.none.no_name.domain.auth.jwt.filter.JwtRefreshFilter;
import com.none.no_name.domain.auth.jwt.filter.JwtVerificationFilter;
import com.none.no_name.domain.auth.jwt.userdetail.CustomUserDetails;
import com.none.no_name.domain.auth.jwt.userdetail.CustomUserDetailsService;
import com.none.no_name.domain.auth.jwt.util.JwtProvider;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	private final JwtProvider jwtProvider;
	private final CustomUserDetailsService customUserDetailsService;

	public SecurityConfig(JwtProvider jwtProvider, CustomUserDetailsService customUserDetailsService) {
		this.jwtProvider = jwtProvider;
		this.customUserDetailsService = customUserDetailsService;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

		httpSecurity.apply(new CustomFilterConfigurer());

		return httpSecurity
			.httpBasic(HttpBasicConfigurer::disable)
			.headers(getHeadersConfigurer())
			.csrf(CsrfConfigurer::disable)
			.cors(getCorsConfigurer())
			.sessionManagement(getSessionManagementConfigurer())
			.exceptionHandling(getExceptionHandlingConfigurer())
			.authorizeHttpRequests(getAuthorizeHttpRequestsConfigurer())
			.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	private class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
		@Override
		public void configure(HttpSecurity builder) {
			AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);

			JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtProvider, authenticationManager);
			jwtAuthenticationFilter.setFilterProcessesUrl(LOGIN_URL);
			// jwtAuthenticationFilter.setAuthenticationSuccessHandler(new MemberAuthenticationSuccessHandler());
			// jwtAuthenticationFilter.setAuthenticationFailureHandler(new MemberAuthenticationFailureHandler());

			JwtRefreshFilter jwtRefreshFilter = new JwtRefreshFilter(jwtProvider);
			JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtProvider, customUserDetailsService);

			builder
				.addFilter(jwtAuthenticationFilter)
				.addFilterAfter(jwtRefreshFilter, JwtAuthenticationFilter.class)
				.addFilterAfter(jwtVerificationFilter, JwtRefreshFilter.class);
		}
	}

}
