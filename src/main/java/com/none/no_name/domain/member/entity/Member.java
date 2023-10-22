package com.none.no_name.domain.member.entity;

import com.none.no_name.domain.memberMusic.entity.MemberMusic;
import com.none.no_name.domain.playList.entity.PlayList;
import com.none.no_name.domain.playListLike.entity.PlayListLike;
import com.none.no_name.global.base.BaseEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Member extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long memberId;

	private String nickname;

	private String email;

	private String password;

	private Authority authority;

	private Status status = Status.MEMBER_ACTIVE;

	@OneToMany(mappedBy = "member", cascade = {CascadeType.ALL})
	private List<MemberMusic> memberMusics = new ArrayList<>();

	@OneToMany(mappedBy = "member", cascade = {CascadeType.ALL})
	private List<PlayList> playLists = new ArrayList<>();

	@OneToMany(mappedBy = "member", cascade = {CascadeType.ALL})
	private List<PlayListLike> likedPlayLists = new ArrayList<>();

	public static Member createMember(String email, String password, String nickname) {
		return Member.builder()
			.email(email)
			.password(password)
			.nickname(nickname)
			.authority(Authority.ROLE_USER)
			.build();
	}

	public enum Status {
		MEMBER_ACTIVE("활동중"),
		MEMBER_DELETE("탈퇴회원");

		private String status;

		Status(String status) {
			this.status = status;
		}
	}
}
