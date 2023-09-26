package com.none.no_name.auth.controller.dto;

import com.none.no_name.auth.oauth.provider.OAuthProvider;

import lombok.Getter;

@Getter
public class OAuthRequest {
	private OAuthProvider provider;
	private String code;
}
