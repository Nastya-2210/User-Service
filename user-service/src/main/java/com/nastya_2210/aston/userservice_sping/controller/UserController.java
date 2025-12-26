package com.nastya_2210.aston.userservice_sping.controller;

import com.nastya_2210.aston.userservice_sping.dto.UserRequestDTO;
import com.nastya_2210.aston.userservice_sping.dto.UserResponseDTO;
import com.nastya_2210.aston.userservice_sping.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Tag(name = "Пользователи", description = "Методы для управления пользователями")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Получить всех пользователей",
            description = "Возвращает список всех пользователей из базы"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Успешный ответ",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserResponseDTO.class))
    )
    @GetMapping
    public List<UserResponseDTO> getAllUsers() {
        return userService.findAllUsers();
    }

    @Operation(
            summary = "Получить одного пользователя",
            description = "Возвращает пользователя по ID из базы"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь найден",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<UserResponseDTO>> getUserById(
            @Parameter(description = "ID пользователя", example = "1")
            @PathVariable int id) {
        UserResponseDTO userDTO = userService.findUser(id);
        EntityModel<UserResponseDTO> userModel = EntityModel.of(userDTO,
                linkTo(methodOn(UserController.class).getUserById(id)).withSelfRel(),
                linkTo(methodOn(UserController.class).getAllUsers()).withRel("all-users"),
                linkTo(methodOn(UserController.class).deleteUser(id)).withRel("delete-user"),
                linkTo(methodOn(UserController.class).updateUser(id, null)).withRel("update-user")
        );

        return ResponseEntity.ok(userModel);
    }

    @Operation(summary = "Создание нового пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Пользователь успешно создан",
                    content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные", content = @Content)
    })
    @PostMapping
    public ResponseEntity<EntityModel<UserResponseDTO>> createUser(
            @RequestBody(
                    description = "Данные нового пользователя",
                    required = true,
                    content = @Content(schema = @Schema(implementation = UserRequestDTO.class))
            )
            @Valid @org.springframework.web.bind.annotation.RequestBody UserRequestDTO userRequestDTO
    ) {
        UserResponseDTO createdUser = userService.saveUser(userRequestDTO);

        EntityModel<UserResponseDTO> model = EntityModel.of(createdUser);
        model.add(linkTo(
                        methodOn(UserController.class).getUserById(createdUser.getId()))
                .withSelfRel());
                        model.add(linkTo(methodOn(UserController.class).getAllUsers()).withRel("all-users"));

        return ResponseEntity
                .created(linkTo(
                                methodOn(UserController.class).getUserById(createdUser.getId()))
                        .toUri())
                .body(model);
    }

    @Operation(
            summary = "Обновить данные пользователя",
            description = "Обновляет данные пользователя по ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно обновлен"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(
            @Parameter(description = "ID пользователя", example = "1")
            @PathVariable int id,
            @RequestBody(
                    description = "Обновленные данные пользователя",
                    required = true,
                    content = @Content(schema = @Schema(implementation = UserRequestDTO.class))
            )
            @Valid @org.springframework.web.bind.annotation.RequestBody UserRequestDTO userRequestDTO
    ) {
        userService.updateUser(id, userRequestDTO);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Удалить пользователя",
            description = "Удаляет пользователя по ID из базы"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно удален"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(
            @Parameter(description = "ID пользователя", example = "42")
            @PathVariable int id
    ) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}
