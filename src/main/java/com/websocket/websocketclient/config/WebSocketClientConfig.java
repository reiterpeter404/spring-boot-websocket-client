package com.websocket.websocketclient.config;

import com.websocket.websocketclient.service.WebSocketClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.util.Base64;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class WebSocketClientConfig {
    private final WebSocketAuthConfig webSocketAuthConfig;
    private final WebSocketClientService webSocketClientService;
    private WebSocketConnectionManager webSocketConnectionManager;

    @Bean
    public WebSocketConnectionManager webSocketConnectionManager() {
        final WebSocketHttpHeaders headers = createAuthHeader();
        this.webSocketConnectionManager = new WebSocketConnectionManager(
                new StandardWebSocketClient(),
                webSocketClientService,
                webSocketAuthConfig.getUrl()
        );
        this.webSocketConnectionManager.setHeaders(headers);
        return webSocketConnectionManager;
    }

    private WebSocketHttpHeaders createAuthHeader() {
        final String auth = webSocketAuthConfig.getUsername() + ":" + webSocketAuthConfig.getPassword();
        final String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
        final WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
        headers.add("Authorization", "Basic " + encodedAuth);
        return headers;
    }

    @Scheduled(fixedDelay = 5000)
    public void checkConnectionAndReconnect() {
        if (webSocketClientService.isConnected()) {
            return;
        }
        if (webSocketConnectionManager.isRunning()) {
            webSocketConnectionManager.stop();
        }
        webSocketConnectionManager.start();
    }
}
