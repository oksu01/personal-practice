package com.none.no_name.domain.member.entity;

import com.none.no_name.global.base.BaseEnum;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Authority implements BaseEnum {
	ROLE_ADMIN("admin"),
	ROLE_USER("user");

	private final String description;

	@Override
	public String getName() {
		return name();
	}

	@Override
	public String getDescription() {
		return this.description;
	}

}
