package com.example.telegrambot.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistorySearchParams {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Nullable
    private LocalDateTime filterFrom;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Nullable
    private LocalDateTime filterTo;

    @Nullable
    private Integer pageSize;
    @Nullable
    private Integer pageNumber;
}
