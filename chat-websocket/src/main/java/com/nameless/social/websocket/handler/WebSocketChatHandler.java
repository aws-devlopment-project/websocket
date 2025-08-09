package com.nameless.social.websocket.handler;

import com.nameless.social.core.dto.ChatPayloadDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class WebSocketChatHandler {
	private final SimpMessagingTemplate messagingTemplate;

	@MessageMapping("/chat.sendMessage")
	public void sendMessage(@Payload ChatPayloadDto chatPayloadDto) {
		log.info("Received message: {} - {}", chatPayloadDto.getSenderEmail(), chatPayloadDto.getMessage());
		messagingTemplate.convertAndSend("/topic/chatroom/" + chatPayloadDto.getClubId(), chatPayloadDto);
	}

	@MessageMapping("/chat.addUser")
	public void addUser(@Payload ChatPayloadDto chatPayloadDto) {
		log.info("User joined: {}", chatPayloadDto.getSenderEmail());
		messagingTemplate.convertAndSend("/topic/chatroom/" + chatPayloadDto.getClubId(), chatPayloadDto);
	}
}
