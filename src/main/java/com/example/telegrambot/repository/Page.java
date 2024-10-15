package com.example.telegrambot.repository;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Page<T> {
    private List<T> content;
    private Integer totalPages;
}
