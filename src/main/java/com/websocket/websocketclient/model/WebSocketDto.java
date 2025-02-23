package com.websocket.websocketclient.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WebSocketDto {
    private UUID uuid;
    private String message;

    @Override
    public String toString() {
        return "WebSocketDto[message = " + message + "]";
    }
}
