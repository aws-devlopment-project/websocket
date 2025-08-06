package com.nameless.social.core.model;

import com.nameless.social.core.dto.MessageDto;
import lombok.Builder;
import lombok.Getter;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.Map;

@Getter
@Builder
public class MessageModel {
	private Long clubId;
	private String messageId;
	private String sender;
	private String message;
	private String timestamp;
	private MessageDto.MessageType type;

	public static MessageModel of(Map<String, AttributeValue> item) {
		return MessageModel.builder()
				.clubId(Long.parseLong(item.get("clubId").n()))
				.messageId(item.get("messageId").s())
				.sender(item.get("sender").s())
				.message(item.get("message").s())
				.timestamp(item.get("timestamp").s())
				.type(MessageDto.MessageType.valueOf(item.get("type").s()))
				.build();
	}
}
