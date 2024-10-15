package com.example.telegrambot;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class WeatherService {
    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

    private final String apiUrl;
    private final String apiKey;

    public WeatherService(@Value("${openweathermap.api.url}") String apiUrl, @Value("${openweathermap.api.key}") String apiKey) {
        this.apiUrl = apiUrl;
        this.apiKey = apiKey;
    }

    public String processCommand(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();

            if (messageText.startsWith("/weather")) {
                String[] messageParts = messageText.split(" ");

                if (messageParts.length == 2) {
                    String city = messageParts[1];

                    return getWeatherData(city);
                } else {
                    return "Укажите город, например: /weather Москва";
                }
            } else {
                return "Неправильная команда, например: /weather Москва";
            }
        }
        return "Неверная команда";
    }

    private String getWeatherData(String city) {
        String url = this.apiUrl + city + "&appid=" + apiKey + "&units=metric&lang=ru";

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            String response = EntityUtils.toString(httpClient.execute(request).getEntity());

            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(response);

            String code = node.get("cod").asText();
            if (code.equals("200")) {
                double temp = node.get("main").get("temp").asDouble();
                double feelsLike = node.get("main").get("feels_like").asDouble();
                String description = node.get("weather").get(0).get("description").asText();
                int humidity = node.get("main").get("humidity").asInt();
                double windSpeed = node.get("wind").get("speed").asDouble();

                return String.format("Температура: %.1f°C, Ощущается как: %.1f°C, Погода: %s, Влажность: %d%%, Ветер: %.1f м/с",
                        temp, feelsLike, description, humidity, windSpeed);
            } else {
                return node.get("message").asText();
            }
        } catch (Exception e) {
            logger.error(BotExceptions.FETCHING_WEATHER_DATA_ERROR, e);
            return BotExceptions.FETCHING_WEATHER_DATA_ERROR;
        }
    }
}
