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
public class MessageDto {
	private Long clubId;
	private String messageId;
	private String sender; // Display purposes
	private String message;
	private LocalDateTime timestamp;
	private MessageType type; // JOIN, CHAT, LEAVE

	public enum MessageType {
		JOIN, CHAT, LEAVE
	}
}
