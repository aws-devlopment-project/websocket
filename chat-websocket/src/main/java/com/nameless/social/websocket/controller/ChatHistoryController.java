package com.nameless.social.websocket.controller;

import com.nameless.social.websocket.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatHistoryController {
	private final ChatMessageService chatMessageService;

	@GetMapping("/history/{clubId}")
	public ResponseEntity<Object> getChatHistory(@PathVariable("clubId") final long clubId) {
		return ResponseEntity.ok(chatMessageService.findByClubId(clubId));
	}
}