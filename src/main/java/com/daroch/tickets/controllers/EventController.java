package com.daroch.tickets.controllers;

import com.daroch.tickets.domain.CreateEventRequest;
import com.daroch.tickets.domain.dtos.CreateEventRequestDto;
import com.daroch.tickets.domain.dtos.CreateEventResponseDto;
import com.daroch.tickets.domain.entities.Event;
import com.daroch.tickets.mappers.EventMapper;
import com.daroch.tickets.services.EventService;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/events")
@RequiredArgsConstructor
public class EventController {
  private final EventMapper eventMapper;
  private final EventService eventService;

  @PostMapping
  public ResponseEntity<CreateEventResponseDto> createEvent(
      @AuthenticationPrincipal Jwt jwt,
      @Valid @RequestBody CreateEventRequestDto createEventRequestDto) {
    CreateEventRequest createEventRequest = eventMapper.fromDto(createEventRequestDto);

    UUID userId = UUID.fromString(jwt.getSubject());

    Event createEvent = eventService.createEvent(userId, createEventRequest);
    CreateEventResponseDto createEventResponseDto = eventMapper.toDto(createEvent);
    return new ResponseEntity<>(createEventResponseDto, HttpStatus.CREATED);
  }
}
