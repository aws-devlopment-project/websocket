package com.nameless.social.websocket.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Configuration
public class DynamoDBConfig {
	@Value("${aws.dynamodb.region}")
	private String region;
	@Value("${aws.dynamodb.access-key}")
	private String accessKey;
	@Value("${aws.dynamodb.secret-key}")
	private String secretKey;

	@Bean
	public DynamoDbClient dynamoDbClient() {
		return DynamoDbClient.builder()
				.region(Region.of(region))
				.credentialsProvider(DefaultCredentialsProvider.create()) // IAM 역할이나 환경변수 사용
				.build();
	}
}