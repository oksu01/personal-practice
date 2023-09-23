package com.none.no_name.domain.auth.jwt.userdetail;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.none.no_name.domain.member.entity.Member;

import lombok.Getter;

@Getter
public class CustomUserDetails implements UserDetails {
	private Member member;

	public CustomUserDetails(Member member) {
		this.member = member;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singleton(
			new SimpleGrantedAuthority(member.getAuthority().toString())
		);
	}

	@Override
	public String getUsername() {
		return member.getEmail();
	}

	@Override
	public String getPassword() {
		return member.getPassword();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	} // 계정 만료 여부 (계정의 유효기간이 지난 경우)

	@Override
	public boolean isAccountNonLocked() {
		return true;
	} // 계정 잠금 여부 (여러 번의 로그인 실패 시)

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	} // 계정의 비밀번호가 만료되었는지 (비밀번호 변경 주기가 지난 경우)

	@Override
	public boolean isEnabled() {
		return true;
	} // 계정이 일시적으로 잠긴 상태인지 여부 (관리자에 의해 일시적으로 정지된 계정 같은 경우)
}
