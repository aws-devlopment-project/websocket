package com.nameless.social.websocket.handler;

import com.nameless.social.core.dto.MessageDto;
import com.nameless.social.websocket.service.ChatMessageService;
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
	private final ChatMessageService chatMessageService;

	@MessageMapping("/chat.sendMessage")
	public void sendMessage(@Payload MessageDto chatMessage) {
		log.info("Received message: {}", chatMessage);

		// DynamoDB에 채팅 메시지 저장
		if (chatMessage.getType() == MessageDto.MessageType.CHAT) {
			chatMessageService.save(chatMessage);
		}

		// 채팅방 구독자들에게 메시지 전송
		messagingTemplate.convertAndSend("/topic/chatroom/" + chatMessage.getClubId(), chatMessage);
	}

	@MessageMapping("/chat.addUser")
	public void addUser(@Payload MessageDto chatMessage) {
		log.info("User joined: {}", chatMessage);
		messagingTemplate.convertAndSend("/topic/chatroom/" + chatMessage.getClubId(), chatMessage);
	}
}
