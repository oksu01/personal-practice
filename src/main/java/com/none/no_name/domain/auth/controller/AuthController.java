package com.none.no_name.domain.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.none.no_name.domain.member.entity.Authority;
import com.none.no_name.domain.member.entity.Member;
import com.none.no_name.domain.member.repository.MemberRepository;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private MemberRepository memberRepository;
	private PasswordEncoder passwordEncoder;

	public AuthController(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
		this.memberRepository = memberRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@PostMapping("/signup")
	public ResponseEntity<Void> signup() {
		memberRepository.save(
			Member.builder()
				.email("test@email.com")
				.password(passwordEncoder.encode("qwer1234!"))
				.authority(Authority.ROLE_USER)
				.build()
		);

		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping("/test")
	public String test() {
		return "테스트 성공";
	}
}
