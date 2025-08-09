package com.nameless.social.api.model;

import com.nameless.social.api.model.user.UserModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class ClubModel {
	private long clubId;
	private String name;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

//	public static ClubModel of(Club club) {
//		return ClubModel.builder()
//				.id(club.getId())
//				.name(club.getName())
//				.participants(club.getParticipants().stream().map(ClubModel::toUserModel).collect(Collectors.toList()))
//				.createdAt(club.getCreatedAt())
//				.updatedAt(club.getUpdatedAt())
//				.build();
//	}
//
//	private static UserModel toUserModel(UserClub userClub) {
//		return UserModel.builder()
//				.id(userClub.getUser().getEmail())
//				.name(userClub.getUser().getName())
//				.status(UserModel.Status.ONLINE)
//				.joinDate(userClub.getUser().getCreatedAt())
//				.lastSeen(userClub.getUser().getUpdatedAt())
//				.build();
//	}
}
