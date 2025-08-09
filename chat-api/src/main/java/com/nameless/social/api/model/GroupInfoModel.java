package com.nameless.social.api.model;

import com.nameless.social.core.entity.Club;
import com.nameless.social.core.entity.Group;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class GroupInfoModel {
	private long groupId;
	private String name;
	private String description;
	private String icon;
	private long memberNum;
	private List<String> questList;
	private List<Long> questSuccessNum;
	private List<String> tag;
	private List<ClubGroupInfoModel> clubList;
	private LocalDate questCreateTime;

	public static GroupInfoModel of(
			final Group group,
			final List<Club> clubs,
			final long clubMemberNum,
			final List<QuestModel> questModels,
			final List<String> tags
	) {
		List<ClubGroupInfoModel> clubGroupInfoModels = new ArrayList<>();
		for (Club club : clubs) {
			clubGroupInfoModels.add(
					ClubGroupInfoModel.builder()
							.clubId(club.getId())
							.name(club.getName())
							.description(club.getDescription())
							.icon("mock icon") // TODO club db에는 icon 컬럼이 없음.
							.memberNum(clubMemberNum)
							.tag(List.of("mock tag")) // TODO club DB에는 club tag 컬럼이 없음.
							.build()
			);
		}

		return GroupInfoModel.builder()
				.groupId(group.getId())
				.name(group.getName())
				.description(group.getDescription())
				.icon(group.getIcon())
				.memberNum(10L) // TODO mock
				.questList(List.of("quest test")) // TODO mock
				.questSuccessNum(List.of(10L)) // TODO mock
				.tag(tags)
				.clubList(clubGroupInfoModels)
				.questCreateTime(LocalDate.now()) // TODO mock
				.build();
	}
}
