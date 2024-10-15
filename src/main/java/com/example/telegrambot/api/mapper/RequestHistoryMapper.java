package com.example.telegrambot.api.mapper;

import com.example.telegrambot.api.model.RequestHistory;
import com.example.telegrambot.api.model.TableContent;
import com.example.telegrambot.repository.BotLog;
import com.example.telegrambot.repository.Page;
import org.springframework.stereotype.Component;

@Component
public class RequestHistoryMapper {

    public TableContent mapToRest(Page<BotLog> data) {
        return TableContent.builder()
                .content(data.getContent().stream().map(this::mapToRest).toList())
                .totalPages(data.getTotalPages())
                .build();
    }

    private RequestHistory mapToRest(BotLog log) {
        return RequestHistory.builder()
                .request(log.getCommand())
                .response(log.getBotResponse())
                .requestTime(log.getRequestTime())
                .build();
    }
}
