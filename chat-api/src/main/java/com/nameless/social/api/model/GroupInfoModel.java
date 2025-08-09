package com.nameless.social.api.model;

import com.nameless.social.core.entity.Group;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
public class GroupInfoModel {
	private long id; // group id
	private String name;
	private String description;
	private String icon;
	private long memberNum;
	private List<String> questList;
	private List<Long> questSuccessNum;
	private List<String> tag;
	private List<ClubGroupInfoModel> clubList;
	private LocalDate questCreateTime;

	public static GroupInfoModel of(Group group, List<String> tags) {
		return GroupInfoModel.builder()
				.id(group.getId())
				.name(group.getName())
				.description(group.getDescription())
				.icon(group.getIcon())
				.memberNum(10L) // TODO mock
				.questList(List.of("quest test")) // TODO mock
				.questSuccessNum(List.of(10L)) // TODO mock
				.tag(tags)
//				.clubList(List.of(new ClubGroupInfoModel()))
				.questCreateTime(LocalDate.now()) // TODO mock
				.build();
	}
}
