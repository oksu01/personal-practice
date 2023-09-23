package com.none.no_name.domain.auth.jwt.userdetail;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.none.no_name.domain.member.entity.Member;
import com.none.no_name.domain.member.repository.MemberRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private MemberRepository memberRepository;

	public CustomUserDetailsService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// todo : 이메일이 존재 하지 않을 시 존재 하지 않는 회원 예외 발생 시키기
		Member member = memberRepository.findByEmail(username).orElseThrow();

		return new CustomUserDetails(member);
	}
}
