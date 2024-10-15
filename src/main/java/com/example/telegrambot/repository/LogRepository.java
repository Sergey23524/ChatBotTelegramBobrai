package com.example.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public abstract interface LogRepository extends JpaRepository<BotLog, Long> {
}
