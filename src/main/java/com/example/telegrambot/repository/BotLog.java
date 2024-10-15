package com.example.telegrambot.repository;

import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
public class BotLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;  // ID пользователя Telegram
    private String command;  // Команда, отправленная пользователем
    private LocalDateTime requestTime;  // Время запроса
    private String botResponse;  // Ответ бота

    // Конструктор по умолчанию
    public BotLog() {}

    // Конструктор с параметрами для удобного создания объекта
    public BotLog(Long userId, String command, LocalDateTime requestTime, String botResponse) {
        this.userId = userId;
        this.command = command;
        this.requestTime = requestTime;
        this.botResponse = botResponse;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public LocalDateTime getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(LocalDateTime requestTime) {
        this.requestTime = requestTime;
    }

    public String getBotResponse() {
        return botResponse;
    }

    public void setBotResponse(String botResponse) {
        this.botResponse = botResponse;
    }
}

