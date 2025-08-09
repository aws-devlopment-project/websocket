package com.nameless.social.core.entity;

import com.nameless.social.core.dto.ChatPayloadDto;
import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@DynamoDbBean
@Setter
@NoArgsConstructor // TODO 꼭 있어야 함. social.core.entity.ChatMessage' appears to have no default constructor thus cannot be used with the BeanTableSchema
@AllArgsConstructor
@Builder
public class ChatMessage {
	private long id; // clubId
	private String messageId;
	@Getter private String senderEmail;
	@Getter private String message;
	@Getter private LocalDateTime createdAt;

	@DynamoDbPartitionKey
	public long getId() {
		return id;
	}

	@DynamoDbSortKey
	public String getMessageId() {
		return messageId;
	}

	public static ChatMessage of(final ChatPayloadDto dto) {
		return ChatMessage.builder()
				.id(dto.getClubId())
				.messageId(String.format("%s#%s", Instant.now().toString(), UUID.randomUUID()))
				.senderEmail(dto.getSenderEmail())
				.message(dto.getMessage())
				.createdAt(LocalDateTime.now())
				.build();
	}
}
