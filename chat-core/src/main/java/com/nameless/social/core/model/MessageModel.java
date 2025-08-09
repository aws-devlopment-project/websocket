package com.nameless.social.core.model;

import com.nameless.social.core.dto.ChatPayloadDto;
import com.nameless.social.core.entity.ChatMessage;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MessageModel {
	private long clubId;
	private String messageId;
	private String senderEmail;
	private String message;
	private String timestamp;
	private ChatPayloadDto.MessageType type;

	public static MessageModel of(final ChatMessage chatMessage) {
		return MessageModel.builder()
				.clubId(chatMessage.getId())
				.messageId(chatMessage.getMessageId())
				.senderEmail(String.valueOf(chatMessage.getSenderEmail()))
				.message(chatMessage.getMessage())
				.timestamp(chatMessage.getCreatedAt().toString())
				.type(ChatPayloadDto.MessageType.CHAT)
				.build();
	}
}
