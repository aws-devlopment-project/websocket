package com.nameless.social.websocket.service;

import com.nameless.social.core.dto.ChatMessageDto;
import com.nameless.social.core.entity.ChatMessage;
import com.nameless.social.websocket.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatMessageService {
	private final ChatMessageRepository chatMessageRepository;

	public void saveChatMessage(final ChatMessageDto chatMessageDto) {
		try {
			ChatMessage chatMessage = ChatMessage.of(
					chatMessageDto.getClubId(),
					chatMessageDto.getSenderId(),
					chatMessageDto.getMessage()
			);

			chatMessageRepository.save(chatMessage);
			log.info("Chat message saved to DynamoDB: {}", chatMessage.getMessageId());

		} catch (Exception e) {
			log.error("Failed to save chat message to DynamoDB", e);
			throw new RuntimeException("Failed to save chat message", e);
		}
	}

	public List<ChatMessage> getChatHistory(Long chatRoomId) {
		return chatMessageRepository.findByChatRoomId(chatRoomId);
	}
}