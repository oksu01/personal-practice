package com.none.no_name.auth.oauth.service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OAuthResult {

	private String accessToken;
	private String refreshToken;
}
