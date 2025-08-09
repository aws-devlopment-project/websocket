package com.nameless.social.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Builder
public class ClubGroupInfoModel {
	private long clubId;
	private String name;
	private String description;
	private String icon;
	private long memberNum;
	private List<String> tag;
}
