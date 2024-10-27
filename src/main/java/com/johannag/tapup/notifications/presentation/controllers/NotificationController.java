package com.johannag.tapup.notifications.presentation.controllers;

import com.johannag.tapup.auth.presentation.annotations.SelfAccessControl;
import com.johannag.tapup.globals.presentation.errors.ErrorResponse;
import com.johannag.tapup.notifications.application.dtos.FindNotificationsDTO;
import com.johannag.tapup.notifications.application.exceptions.NotificationNotFoundException;
import com.johannag.tapup.notifications.application.mappers.NotificationApplicationMapper;
import com.johannag.tapup.notifications.application.services.NotificationService;
import com.johannag.tapup.notifications.domain.models.NotificationModel;
import com.johannag.tapup.notifications.presentation.dtos.queries.FindNotificationsQuery;
import com.johannag.tapup.notifications.presentation.dtos.requests.UpdateNotificationReadStatusRequestDTO;
import com.johannag.tapup.notifications.presentation.dtos.responses.NotificationResponseDTO;
import com.johannag.tapup.notifications.presentation.mappers.NotificationPresentationMapper;
import com.johannag.tapup.notifications.presentation.schemas.PageNotificationResponseDTO;
import com.johannag.tapup.users.application.exceptions.UserNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@ResponseBody
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1")
@Tag(name = "Users")
public class NotificationController {

    private final NotificationService notificationService;
    private final NotificationApplicationMapper notificationApplicationMapper;
    private final NotificationPresentationMapper notificationPresentationMapper;

    @Operation(summary = "Find all notifications for user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notifications found successfully", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = PageNotificationResponseDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized: Invalid credentials", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "403", description = "Forbidden: Not enough privileges", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Not Found: User Not Found", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))})
    })
    @SelfAccessControl
    @PreAuthorize("hasAnyAuthority({'ADMIN','REGULAR'})")
    @GetMapping("/users/{userUuid}/notifications")
    public ResponseEntity<Page<NotificationResponseDTO>> findByUser(@PathVariable UUID userUuid,
                                                                    @Valid @ParameterObject @ModelAttribute FindNotificationsQuery findNotificationsQuery)
            throws UserNotFoundException {

        FindNotificationsDTO dto = notificationApplicationMapper.toFindDTO(findNotificationsQuery, userUuid);
        Page<NotificationModel> notifications = notificationService.findByUser(dto);
        Page<NotificationResponseDTO> response = notificationPresentationMapper.toResponseDTO(notifications);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Update notification read status for user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notifications updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized: Invalid credentials", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "403", description = "Forbidden: Not enough privileges", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Not Found: Resource Not Found", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))})
    })
    @SelfAccessControl
    @PreAuthorize("hasAnyAuthority({'ADMIN','REGULAR'})")
    @PatchMapping("/users/{userUuid}/notifications/{notificationUuid}")
    public ResponseEntity<NotificationResponseDTO> updateReadStatusByUser(@PathVariable UUID userUuid,
                                                                          @PathVariable UUID notificationUuid,
                                                                          @Valid @RequestBody UpdateNotificationReadStatusRequestDTO request)
            throws UserNotFoundException, NotificationNotFoundException {

        var dto = notificationApplicationMapper.toUpdateDTO(userUuid, notificationUuid, request);
        NotificationModel notification = notificationService.updateReadStatusByUser(dto);
        NotificationResponseDTO response = notificationPresentationMapper.toResponseDTO(notification);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
