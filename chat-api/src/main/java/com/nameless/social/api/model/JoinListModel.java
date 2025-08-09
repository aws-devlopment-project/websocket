package com.nameless.social.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class JoinListModel {
	private long groupId;
	private String groupname;
	private List<ClubModel> clubList;
}
