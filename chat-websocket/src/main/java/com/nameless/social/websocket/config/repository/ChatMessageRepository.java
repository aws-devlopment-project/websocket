package com.nameless.social.websocket.config.repository;

import com.nameless.social.core.entity.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatMessageRepository {
	private final DynamoDbClient dynamoDbClient;
	private final DynamoDbEnhancedClient enhancedClient;

	private static final String TABLE_NAME = "Chat_Message";

	/**
	 * DynamoDB 테이블 생성
	 */
	public void createTable() {
		try {
			dynamoDbClient.createTable(CreateTableRequest.builder()
					.tableName(TABLE_NAME)
					.attributeDefinitions(
							AttributeDefinition.builder()
									.attributeName("id")
									.attributeType(ScalarAttributeType.N)
									.build()
					)
					.keySchema(
							KeySchemaElement.builder()
									.attributeName("id")
									.keyType(KeyType.HASH)
									.build()
					)
					.provisionedThroughput(
							ProvisionedThroughput.builder()
									.readCapacityUnits(5L)
									.writeCapacityUnits(5L)
									.build()
					)
					.build());

			log.info("테이블 생성 완료 - {}", TABLE_NAME);

		} catch (ResourceInUseException e) {
			log.warn("테이블이 이미 존재합니다: - {}", TABLE_NAME);
		}
	}

	/**
	 * 데이터 저장 (Enhanced Client 사용)
	 * @param chatMessage
	 */
	public void insertChatMessage(final ChatMessage chatMessage) {
		DynamoDbTable<ChatMessage> table = enhancedClient.table(TABLE_NAME, TableSchema.fromBean(ChatMessage.class));
		table.putItem(chatMessage);
		log.info(">>> table 저장 완료!!");
	}

	/**
	 * clubId(roomId)로 채팅 메시지 내역 조회
	 * @param clubId
	 * @return
	 */
	public ChatMessage findByClubId(final long clubId) {
		return null;
//		GetItemRequest request = GetItemRequest.builder()
//				.tableName(TABLE_NAME)
//				.key(Map.of("clubId", AttributeValue.fromN(String.valueOf(clubId))))
//				.build();
//
//		GetItemResponse response = dynamoDbClient.getItem(request);
//
//		if (response.hasItem()) {
//			Map<String, AttributeValue> item = response.item();
//			return MessageModel.of(item);
//
//		} else {
//			return null;
//		}


//		GetItemRequest request = GetItemRequest.builder()
//				.tableName(TABLE_NAME)
//				.key(Map.of("clubId", AttributeValue.fromS(String.valueOf(clubId))))
//				.build();
//
//		GetItemResponse response = dynamoDbClient.getItem(request);
//
//		if (response.hasItem()) {
//			Map<String, AttributeValue> item = response.item();
//			return MessageModel.of(item);
//
//		} else {
//			return null;
//		}
	}

//	public void update(String messageId, String newContent) {
//		UpdateItemRequest request = UpdateItemRequest.builder()
//				.tableName(TABLE_NAME)
//				.key(Map.of("messageId", AttributeValue.fromS(messageId)))
//				.updateExpression("SET content = :content")
//				.expressionAttributeValues(Map.of(":content", AttributeValue.fromS(newContent)))
//				.build();
//
//		dynamoDbClient.updateItem(request);
//	}
//
//	public void delete(String messageId) {
//		DeleteItemRequest request = DeleteItemRequest.builder()
//				.tableName(TABLE_NAME)
//				.key(Map.of("messageId", AttributeValue.fromS(messageId)))
//				.build();
//
//		dynamoDbClient.deleteItem(request);
//	}
}
