package com.nameless.social.api.service;

import com.nameless.social.api.model.GroupInfoModel;
import com.nameless.social.api.model.GroupModel;
import com.nameless.social.api.repository.ClubRepository;
import com.nameless.social.api.repository.GroupRepository;
import com.nameless.social.api.repository.user.UserRepository;
import com.nameless.social.core.entity.Club;
import com.nameless.social.core.entity.Group;
import com.nameless.social.core.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GroupServiceTest {
	@InjectMocks
	private GroupService groupService;

	@Mock
	private UserRepository userRepository;
	@Mock
	private GroupRepository groupRepository;
	@Mock
	private ClubRepository clubRepository;

	private User user;
	private Group group;
	private Club club;

	@BeforeEach
	void setUp() {
		user = new User("test token", "test", "test@test.com");
		group = new Group(1L, "test Group", "[\"절약\", \"투자\", \"예산관리\", \"자동화\"]");
		club = new Club(1L, "ClubA");
	}

	@Test
	@DisplayName("유저 이메일로 그룹 단 건 조회")
	void getGroupByUserEmailTest() {
		// given
		given(userRepository.findByEmail(user.getEmail())).willReturn(Optional.of(user));

		// when
		GroupModel result = groupService.getGroupByUserEmail(user.getEmail());

		// then
		assertThat(result).isNotNull();
	}

	@Test
	@DisplayName("그룹 정보 조회")
	void getGroupInfoTest() {
		// given
		given(groupRepository.findByName(group.getName())).willReturn(Optional.of(group));
		given(clubRepository.findAllByGroupId(group.getId())).willReturn(List.of(club));

		// when
		GroupInfoModel result = groupService.getGroupInfo(group.getName());

		// then
		assertThat(result.getName()).isEqualTo("test Group");
		assertThat(result.getGroupId()).isEqualTo(1L);
		assertThat(result.getClubList().get(0).getName()).isEqualTo("ClubA");
	}

	@Test
	@DisplayName("그룹 목록 조회")
	void getGroupListTest() {
		// given
		given(groupRepository.findAll()).willReturn(List.of(group));
		given(clubRepository.findAllByGroupId(group.getId())).willReturn(List.of(club));

		// when
		List<GroupInfoModel> result = groupService.getGroupList();

		// then
		assertThat(result).isNotNull();
		assertThat(result.get(0).getName()).isEqualTo("test Group");
		assertThat(result.get(0).getGroupId()).isEqualTo(1L);
		assertThat(result.get(0).getClubList().get(0).getName()).isEqualTo("ClubA");
	}
}
