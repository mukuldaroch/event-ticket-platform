package com.daroch.tickets.controllers;

import com.daroch.tickets.domain.CreateEventRequest;
import com.daroch.tickets.domain.dtos.CreateEventRequestDto;
import com.daroch.tickets.domain.dtos.CreateEventResponseDto;
import com.daroch.tickets.domain.dtos.GetEventDetailsResponseDto;
import com.daroch.tickets.domain.dtos.ListEventResponseDto;
import com.daroch.tickets.domain.entities.Event;
import com.daroch.tickets.mappers.EventMapper;
import com.daroch.tickets.services.EventService;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

  private UUID parseUserId(Jwt jwt) {
    return UUID.fromString(jwt.getSubject());
  }

  // ---------------------------------------------
  // ---------------------------------------------
  @PostMapping
  public ResponseEntity<CreateEventResponseDto> createEvent(
      @AuthenticationPrincipal Jwt jwt,
      @Valid @RequestBody CreateEventRequestDto createEventRequestDto) {
    CreateEventRequest createEventRequest = eventMapper.fromDto(createEventRequestDto);

    UUID userId = parseUserId(jwt);

    Event createEvent = eventService.createEvent(userId, createEventRequest);
    CreateEventResponseDto createEventResponseDto =
        eventMapper.toCreateEventResponseDto(createEvent);
    return new ResponseEntity<>(createEventResponseDto, HttpStatus.CREATED);
  }

  // ---------------------------------------------
  // ---------------------------------------------
  @GetMapping
  public ResponseEntity<Page<ListEventResponseDto>> listEvents(
      @AuthenticationPrincipal Jwt jwt, Pageable pageable) {
    UUID userId = parseUserId(jwt);
    Page<Event> events = eventService.listEventsForOrganizer(userId, pageable);

    return ResponseEntity.ok(events.map(eventMapper::tolistEventResponseDto));
  }

  // ---------------------------------------------
  // ---------------------------------------------
  @GetMapping("/{eventId}")
  public ResponseEntity<GetEventDetailsResponseDto> getEvent(
      @AuthenticationPrincipal Jwt jwt, @PathVariable UUID eventId) {

    UUID organizerId = parseUserId(jwt);

    return eventService
        .getEventForOrganizer(organizerId, eventId)
        .map(eventMapper::toGetEventDetailsResponseDto)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
    // .build() means: “no body, just status and headers.”
  }
}
