package com.websocket.websocketclient.controller;

import com.websocket.websocketclient.model.WebSocketDto;
import com.websocket.websocketclient.service.WebSocketClientService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class MessageController {
    private final WebSocketClientService webSocketClientService;

    @PostMapping("/send")
    public void broadcastMessageToAllSessions(@RequestBody WebSocketDto messageDto) {
        webSocketClientService.sendMessage(messageDto);
    }
}
