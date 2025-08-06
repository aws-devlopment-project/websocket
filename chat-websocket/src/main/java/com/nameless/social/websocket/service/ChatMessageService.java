package com.nameless.social.websocket.service;

import com.nameless.social.core.dto.MessageDto;
import com.nameless.social.core.model.MessageModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatMessageService {
	private final DynamoDbClient dynamoDbClient;
	private static final String TABLE_NAME = "Message";

	public void save(final MessageDto messageDto) {
		log.info("message save start: {}", messageDto.getMessageId());

		PutItemRequest request = PutItemRequest.builder()
				.tableName(TABLE_NAME)
				.item(Map.of(
						"messageId", AttributeValue.fromS(messageDto.getMessageId()),
						"sender", AttributeValue.fromS(messageDto.getSender()),
						"message", AttributeValue.fromS(messageDto.getMessage()),
						"timestamp", AttributeValue.fromN(String.valueOf(messageDto.getTimestamp())),
						"messageType", AttributeValue.fromS(messageDto.getType().name())
				))
				.build();
		dynamoDbClient.putItem(request);

		log.info("message saved to DynamoDB: {}", messageDto.getMessage());
	}

	public MessageModel findByClubId(final long clubId) {
		GetItemRequest request = GetItemRequest.builder()
				.tableName(TABLE_NAME)
				.key(Map.of("clubId", AttributeValue.fromN(String.valueOf(clubId))))
				.build();

		GetItemResponse response = dynamoDbClient.getItem(request);

		if (response.hasItem()) {
			Map<String, AttributeValue> item = response.item();
			return MessageModel.of(item);

		} else {
			return null;
		}
	}

	public MessageModel findById(long clubId) {
		GetItemRequest request = GetItemRequest.builder()
				.tableName(TABLE_NAME)
				.key(Map.of("clubId", AttributeValue.fromS(String.valueOf(clubId))))
				.build();

		GetItemResponse response = dynamoDbClient.getItem(request);

		if (response.hasItem()) {
			Map<String, AttributeValue> item = response.item();
			return MessageModel.of(item);

		} else {
			return null;
		}
	}

	public void update(String messageId, String newContent) {
		UpdateItemRequest request = UpdateItemRequest.builder()
				.tableName(TABLE_NAME)
				.key(Map.of("messageId", AttributeValue.fromS(messageId)))
				.updateExpression("SET content = :content")
				.expressionAttributeValues(Map.of(":content", AttributeValue.fromS(newContent)))
				.build();

		dynamoDbClient.updateItem(request);
	}

	public void delete(String messageId) {
		DeleteItemRequest request = DeleteItemRequest.builder()
				.tableName(TABLE_NAME)
				.key(Map.of("messageId", AttributeValue.fromS(messageId)))
				.build();

		dynamoDbClient.deleteItem(request);
	}
}