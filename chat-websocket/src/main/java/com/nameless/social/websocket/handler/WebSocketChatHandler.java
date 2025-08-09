package com.nameless.social.websocket.handler;

import com.nameless.social.core.dto.ChatPayloadDto;
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
	public void sendMessage(@Payload ChatPayloadDto chatPayloadDto) {
		log.info("Received message: {} - {}", chatPayloadDto.getSenderEmail(), chatPayloadDto.getMessage());
		messagingTemplate.convertAndSend("/topic/chatroom/" + chatPayloadDto.getClubId(), chatPayloadDto);

		// DynamoDB에 채팅 메시지 저장
		chatMessageService.createTable(); // TODO DynamoDB 접근을 매번 하지 않도록 수정 필요. 서버 처음 구동시 테이블 생성하는 코드부터 한 번만 호출하도록 하기.
		if (chatPayloadDto.getType() == ChatPayloadDto.MessageType.CHAT) {
			chatMessageService.save(chatPayloadDto);
		}

		// 채팅방 구독자들에게 메시지 전송
		messagingTemplate.convertAndSend("/topic/chatroom/" + chatPayloadDto.getClubId(), chatPayloadDto);
	}

	@MessageMapping("/chat.addUser")
	public void addUser(@Payload ChatPayloadDto chatPayloadDto) {
		log.info("User joined: {}", chatPayloadDto.getSenderEmail());
		messagingTemplate.convertAndSend("/topic/chatroom/" + chatPayloadDto.getClubId(), chatPayloadDto);
	}
}
