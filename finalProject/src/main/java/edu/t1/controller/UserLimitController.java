package edu.t1.controller;

import edu.t1.dto.OperReqDto;
import edu.t1.dto.UserLimitDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Limits", description = "Управление лимитами пользователей")
@RequestMapping("/limits")
public interface UserLimitController {

    @Operation(
            summary = "Получить все лимиты пользователей",
            description = "Возвращает список всех лимитов в системе",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Список лимитов успешно получен"
                    )
            }
    )
    @GetMapping
    List<UserLimitDto> getAllUserLimit();

    @Operation(
            summary = "Получить лимит по ID пользователя",
            description = "Находит лимит по уникальному ID пользователя, либо создает новый",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Лимит найден или создан"
                    )
            },
            parameters = @Parameter(name = "id", description = "ID пользователя", example = "1")
    )
    @GetMapping("/{id}")
    UserLimitDto getUserLimitById(@PathVariable Long id);

    @Operation(
            summary = "Добавить новую операцию",
            description = "Создаёт новую операцию на основе переданных данных",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Тело запроса с описанием операции",
                    required = true,
                    content = @Content(
                            examples = @ExampleObject(
                                    name = "Пример операции",
                                    value = "{ \"userid\": \"1\", \"amount\": \"10.0\", \"date\": \"2025-09-25\" }"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Операция добавлена, лимит изменен"
                    ),
                    @ApiResponse(
                            responseCode = "404", description = "Лимит не найден"
                    ),
                    @ApiResponse(
                            responseCode = "400", description = "Операция отклонена, лимит превышен"
                    )
            }
    )
    @PostMapping
    UserLimitDto addOperation(@RequestBody @Valid OperReqDto operReqDto);

    @Operation(
            summary = "Исполнить операцию на сумму",
            description = "Создаёт новую операцию на переданную сумму",
            parameters = {
                    @Parameter(name = "id", description = "ID пользователя", example = "1"),
                    @Parameter(name = "amount", description = "Сумма операции", example = "10.0")
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Операция добавлена, лимит изменен"
                    ),
                    @ApiResponse(
                            responseCode = "404", description = "Лимит не найден"
                    ),
                    @ApiResponse(
                            responseCode = "400", description = "Операция отклонена, лимит превышен"
                    )
            }
    )
    @PostMapping("/pay/{id}")
    UserLimitDto payAmount(@PathVariable Long id, @RequestParam Double amount);


    @Operation(
            summary = "Добавить новый лимит",
            description = "Создаёт новый лимит для пользователя на основе id и лимита по умолчанию",
            parameters = @Parameter(name = "id", description = "ID пользователя", example = "1"),
            responses = {
            @ApiResponse(
                    responseCode = "200", description = "Лимит добавлен"
            ),
            @ApiResponse(
                    responseCode = "400", description = "Лимит с таким ID уже существует"
            )
            }
    )
    @GetMapping("/new/{id}")
    UserLimitDto createUserLimitById(@PathVariable Long id);

    @Operation(
            summary = "Отменить операцию у пользователя",
            description = "Изменяет статус указанной операции на DELETED",
            parameters = {
                    @Parameter(name = "id", description = "ID пользователя", example = "1"),
                    @Parameter(name = "operId", description = "ID операции", example = "1")
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Операция удалена, лимит восстановлен"
                    ),
                    @ApiResponse(
                            responseCode = "404", description = "Лимит или операция у пользователя не найдены"
                    )
            }
    )
    @PutMapping("/{id}")
    UserLimitDto cancelOperation(@PathVariable Long id, @RequestParam Long operId);

    @Operation(
            summary = "Сбросить лимит у всех пользователей",
            description = "Восстанавливает текущий лимит у пользователей значением по умолчанию",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Список лимитов успешно обновлен"
                    )
            }
    )
    @PutMapping("/reset")
    void resetCurrLimitOnNewDay();
}