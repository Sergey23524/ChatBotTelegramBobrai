package com.example.telegrambot.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Operator {
    EQ("="),
    GE(">="),
    LE("<=");

    final String op;
}