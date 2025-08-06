package com.nameless.social.core.entity;

import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

import java.time.Instant;
import java.util.UUID;

@DynamoDbBean
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessage {
	private String clubId;
	private String messageId;
	@Getter private Long senderId;
	@Getter private String message;
	@Getter private Instant createdAt;

	@DynamoDbPartitionKey
	public String getClubId() {
		return clubId;
	}

	@DynamoDbSortKey
	public String getMessageId() {
		return messageId;
	}

	public static ChatMessage of(Long clubId, Long senderId, String message) {
		return ChatMessage.builder()
				.clubId("ROOM#" + clubId)
				.messageId(String.format("%s#%s", Instant.now().toString(), UUID.randomUUID()))
				.senderId(senderId)
				.message(message)
				.createdAt(Instant.now())
				.build();
	}
}
