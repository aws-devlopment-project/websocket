package com.nameless.social.core.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatPayloadDto {
	private Long clubId;
	private String senderEmail;
	private String senderUsername; // Display purposes
	private String message;
	private LocalDateTime timestamp;
	private MessageType type;

	public enum MessageType {
		JOIN, CHAT, LEAVE
	}
}
