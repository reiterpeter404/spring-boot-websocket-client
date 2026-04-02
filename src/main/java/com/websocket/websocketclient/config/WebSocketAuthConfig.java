package com.websocket.websocketclient.config;

import com.websocket.websocketclient.enums.AuthenticationMethod;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "websocket.authentication")
public class WebSocketAuthConfig {
    private String url;
    private String username;
    private String password;
    private String jwtSecret;
    private AuthenticationMethod authenticationMethod = AuthenticationMethod.JWT_TOKEN;
}
