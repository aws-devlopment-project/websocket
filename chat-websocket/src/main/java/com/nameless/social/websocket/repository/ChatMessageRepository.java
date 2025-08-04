package com.nameless.social.websocket.repository;

import com.nameless.social.core.entity.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ChatMessageRepository {
	private final DynamoDbEnhancedClient dynamoDbEnhancedClient;

	public void save(ChatMessage chatMessage) {
		DynamoDbTable<ChatMessage> table = getTable();
		table.putItem(chatMessage);
	}

	public List<ChatMessage> findByChatRoomId(Long chatRoomId) {
		DynamoDbTable<ChatMessage> table = getTable();

		QueryConditional queryConditional = QueryConditional
				.keyEqualTo(Key.builder()
						.partitionValue("ROOM#" + chatRoomId)
						.build());

		return table.query(QueryEnhancedRequest.builder()
						.queryConditional(queryConditional)
						.build())
				.items()
				.stream()
				.collect(Collectors.toList());
	}

	private DynamoDbTable<ChatMessage> getTable() {
		return dynamoDbEnhancedClient.table("ChatMessage", TableSchema.fromBean(ChatMessage.class));
	}
}