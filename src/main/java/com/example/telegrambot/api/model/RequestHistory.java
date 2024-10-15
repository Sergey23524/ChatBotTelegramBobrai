package com.example.telegrambot.api.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class RequestHistory {
    private String response;
    private String request;
    private LocalDateTime requestTime;
}
