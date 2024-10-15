package com.example.telegrambot.api;

import com.example.telegrambot.api.mapper.RequestHistoryMapper;
import com.example.telegrambot.api.model.HistorySearchParams;
import com.example.telegrambot.api.model.TableContent;
import com.example.telegrambot.repository.NativeLogRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class WeatherBotController {
    private NativeLogRepository nativeLogRepository;
    private RequestHistoryMapper historyMapper;

    @Operation(summary = "История запросов", description = "Показать историю запросов")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ок",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TableContent.class))}),
            @ApiResponse(responseCode = "500", description = "Ошибка")
    })
    @GetMapping(value = "/logs")
    public ResponseEntity<TableContent> getHistory(@RequestBody HistorySearchParams params) {
        return ResponseEntity.ok(
                historyMapper.mapToRest(
                        nativeLogRepository.getLogHistory(params, null)
                )
        );
    }

    @Operation(summary = "История запросов по юзеру", description = "Показать историб запросов по юзеру")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ок",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TableContent.class))}),
            @ApiResponse(responseCode = "500", description = "Ошибка")
    })
    @GetMapping(value = "/logs/{user_id}")
    public ResponseEntity<TableContent> getPersonalHistory(@PathVariable Long user_id, @RequestBody HistorySearchParams params) {
        return ResponseEntity.ok(
                historyMapper.mapToRest(
                        nativeLogRepository.getLogHistory(params, user_id)
                )
        );    }
}
