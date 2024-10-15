package com.example.telegrambot.api.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TableContent {
    private List<?> content;

    private Integer totalPages;

}
