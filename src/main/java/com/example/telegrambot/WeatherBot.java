package com.example.telegrambot;

import com.example.telegrambot.repository.BotLog;
import com.example.telegrambot.repository.LogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;

@Service
public class WeatherBot extends TelegramLongPollingBot {

    private static final Logger logger = LoggerFactory.getLogger(WeatherBot.class);

    @Value("${telegram.bot.username}")
    private String botName;

    private final LogRepository logRepository;
    private final WeatherService weatherService;

    public WeatherBot(@Value("${telegram.bot.token}") String botToken, WeatherService weatherService, LogRepository logRepository) {
        super(botToken);
        this.weatherService = weatherService;
        this.logRepository = logRepository;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Long userId = update.getMessage().getFrom().getId();
        String command = update.getMessage().getText();
        String response = weatherService.processCommand(update);

        BotLog log = new BotLog(userId, command, LocalDateTime.now(), response);
        logRepository.save(log);

        sendMsg(update.getMessage().getChatId().toString(), response);
    }

    private void sendMsg(String chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            logger.error(BotExceptions.SEND_MESSAGE_ERROR, e);
        }
    }

    @Override
    public String getBotUsername() {
        return botName;
    }
}