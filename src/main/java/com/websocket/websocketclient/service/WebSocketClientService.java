package com.websocket.websocketclient.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.websocket.websocketclient.model.WebSocketDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Service
public class WebSocketClientService extends TextWebSocketHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketClientService.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private WebSocketSession session;

    @Override
    public void afterConnectionEstablished(final WebSocketSession session) {
        this.session = session;
        LOGGER.info("Connected to session {}", session.getId());
    }

    @Override
    public void afterConnectionClosed(final WebSocketSession session, final CloseStatus status) {
        this.session = null;
        final String reason = status.getReason() == null
                ? ""
                : ": " + status.getReason();
        LOGGER.info("Disconnected from session {}{}", session.getId(), reason);
    }

    @Override
    public void handleTextMessage(final WebSocketSession session, final TextMessage message) throws JsonProcessingException {
        final WebSocketDto messageDto = OBJECT_MAPPER.readValue(message.getPayload(), WebSocketDto.class);
        LOGGER.info("Received message: {}", messageDto);
    }

    @Override
    public void handleTransportError(final WebSocketSession session, final Throwable exception) {
        LOGGER.error("WebSocket transport error:", exception);
    }

    public boolean isConnected() {
        return session != null && session.isOpen();
    }
}
