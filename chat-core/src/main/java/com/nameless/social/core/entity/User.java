package com.nameless.social.core.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.beans.ConstructorProperties;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(unique = true, nullable = false)
	private String token; // AWS Cognito User-sub

	@Column(unique = true, nullable = false)
	private String email;

	@Column(nullable = false)
	private String name;

	@OneToMany(mappedBy = "user")
	private List<UserClub> userClubs = new ArrayList<>();

	@OneToMany(mappedBy = "user")
	private List<UserGroup> userGroups = new ArrayList<>();

	// TODO test를 위해서만 사용 중. 실제 서비스에 사용하려면 사용 가능한지 확인 필요
	@ConstructorProperties({"token", "name", "email"})
	public User(String token, String name, String email) {
		this.token = token;
		this.name = name;
		this.email = email;
	}
}
