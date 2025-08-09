package com.nameless.social.api.repository;

import com.nameless.social.core.entity.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {
	Optional<Club> findByName(String name);
	List<Club> findAllByGroupId(long groupId);

	@Query("SELECT c FROM Club c WHERE c.name IN :clubNames")
	List<Club> findByNameList(@Param("clubNames") List<String> clubNames);
}
