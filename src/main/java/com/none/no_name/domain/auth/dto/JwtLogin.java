package com.none.no_name.domain.auth.dto;

import lombok.Getter;

@Getter
public class JwtLogin {
	private String email;
	private String password;
}
