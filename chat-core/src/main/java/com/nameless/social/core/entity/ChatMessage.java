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
	private String chatRoomId;
	private String messageId;
	@Getter private Long senderId;
	@Getter private String message;
	@Getter private Instant createdAt;

	@DynamoDbPartitionKey
	public String getChatRoomId() {
		return chatRoomId;
	}

	@DynamoDbSortKey
	public String getMessageId() {
		return messageId;
	}

	public static ChatMessage of(Long chatRoomId, Long senderId, String message) {
		return ChatMessage.builder()
				.chatRoomId("ROOM#" + chatRoomId)
				.messageId(String.format("%s#%s", Instant.now().toString(), UUID.randomUUID().toString()))
				.senderId(senderId)
				.message(message)
				.createdAt(Instant.now())
				.build();
	}
}
