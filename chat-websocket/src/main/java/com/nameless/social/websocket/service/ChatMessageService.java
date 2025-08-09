package com.nameless.social.websocket.service;

import com.nameless.social.core.dto.ChatPayloadDto;
import com.nameless.social.core.entity.ChatMessage;
import com.nameless.social.core.model.MessageModel;
import com.nameless.social.websocket.config.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatMessageService {
	private final ChatMessageRepository chatMessageRepository;

	public void createTable() {
		chatMessageRepository.createTable();
	}

	public void save(final ChatPayloadDto dto) {
		log.info("message save start: {} {}", dto.getClubId(), dto.getSenderEmail());

		chatMessageRepository.insertChatMessage(ChatMessage.of(dto));

		log.info("message saved to DynamoDB: {}", dto.getMessage());
	}

	public MessageModel findByClubId(final long clubId) {
		ChatMessage chatMessage = chatMessageRepository.findByClubId(clubId);
		return MessageModel.of(chatMessage);
	}
}