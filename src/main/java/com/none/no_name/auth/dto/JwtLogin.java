package com.none.no_name.auth.dto;

import lombok.Getter;

@Getter
public class JwtLogin {
	private String email;
	private String password;
}
