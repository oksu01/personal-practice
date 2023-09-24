package com.none.no_name.domain.auth.controller.dto;

import com.none.no_name.domain.auth.oauth.provider.OAuthProvider;

import lombok.Getter;

@Getter
public class OAuthRequest {
	private OAuthProvider provider;
	private String code;
}
