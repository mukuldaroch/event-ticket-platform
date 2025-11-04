package com.daroch.tickets.controllers;

import com.daroch.tickets.domain.CreateEventRequest;
import com.daroch.tickets.domain.UpdateEventRequest;
import com.daroch.tickets.domain.dtos.CreateEventRequestDto;
import com.daroch.tickets.domain.dtos.CreateEventResponseDto;
import com.daroch.tickets.domain.dtos.GetEventDetailsResponseDto;
import com.daroch.tickets.domain.dtos.ListEventResponseDto;
import com.daroch.tickets.domain.dtos.UpdateEventRequestDto;
import com.daroch.tickets.domain.dtos.UpdateEventResponseDto;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/events")
@RequiredArgsConstructor
public class EventController {

  private final EventMapper eventMapper;
  private final EventService eventService;

  /**
   * Extracts the user (organizer) ID from the JWT token. The JWT's subject is assumed to store the
   * user's UUID.
   */
  private UUID parseUserId(Jwt jwt) {
    return UUID.fromString(jwt.getSubject());
  }

  /**
   * POST /events Creates a new event for the authenticated organizer.
   *
   * @param jwt JWT token containing the user ID
   * @param createEventRequestDto incoming event creation payload
   * @return 201 Created with event details
   */
  @PostMapping
  public ResponseEntity<CreateEventResponseDto> createEvent(
      @AuthenticationPrincipal Jwt jwt,
      @Valid @RequestBody CreateEventRequestDto createEventRequestDto) {

    // Convert incoming DTO → internal model used by service
    CreateEventRequest createEventRequest = eventMapper.fromDto(createEventRequestDto);

    // Extract organizer/user ID from JWT
    UUID userId = parseUserId(jwt);

    // Delegate the actual creation logic to the service
    Event createdEvent = eventService.createEvent(userId, createEventRequest);

    // Convert the saved entity → response DTO for API response
    CreateEventResponseDto createEventResponseDto =
        eventMapper.toCreateEventResponseDto(createdEvent);

    // Return 201 CREATED with the new event details
    return new ResponseEntity<>(createEventResponseDto, HttpStatus.CREATED);
  }

  /**
   * GET /events Lists all events belonging to the authenticated organizer. Supports pagination via
   * Spring's Pageable.
   *
   * @param jwt JWT token containing user ID
   * @param pageable Spring’s pagination and sorting abstraction
   * @return 200 OK with paginated list of events
   */
  @GetMapping
  public ResponseEntity<Page<ListEventResponseDto>> listEvents(
      @AuthenticationPrincipal Jwt jwt, Pageable pageable) {

    // Extract organizer ID from JWT
    UUID userId = parseUserId(jwt);

    // Fetch paginated events for this organizer
    Page<Event> events = eventService.listEventsForOrganizer(userId, pageable);

    // Map entity page → DTO page
    return ResponseEntity.ok(events.map(eventMapper::tolistEventResponseDto));
  }

  /**
   * GET /events/{eventId} Retrieves details of a specific event if it belongs to the authenticated
   * organizer.
   *
   * @param jwt JWT token containing organizer ID
   * @param eventId ID of the event to fetch
   * @return 200 OK with event details or 404 if not found
   */
  @GetMapping("/{eventId}")
  public ResponseEntity<GetEventDetailsResponseDto> getEvent(
      @AuthenticationPrincipal Jwt jwt, @PathVariable UUID eventId) {

    UUID organizerId = parseUserId(jwt);

    // Find the event for the given organizer
    return eventService
        .getEventForOrganizer(organizerId, eventId)
        .map(eventMapper::toGetEventDetailsResponseDto) // convert to DTO if
        // found
        .map(ResponseEntity::ok) // wrap in 200 OK
        .orElse(ResponseEntity.notFound().build()); // else return 404
    // .build() means no body, only status + headers
  }

  /**
   * POST /events/{eventId} Updates an existing event and its ticket types.
   *
   * @param jwt JWT token containing user ID
   * @param eventId ID of the event to update
   * @param updateEventRequestDto payload with updated fields
   * @return 200 OK with updated event details
   */
  @PutMapping("/{eventId}")
  public ResponseEntity<UpdateEventResponseDto> updateEvent(
      @AuthenticationPrincipal Jwt jwt,
      @PathVariable UUID eventId,
      @Valid @RequestBody UpdateEventRequestDto updateEventRequestDto) {

    // Convert DTO → domain model for service layer
    UpdateEventRequest updateEventRequest =
        eventMapper.fromUpdateEventRequestDto(updateEventRequestDto);

    // Extract user ID from JWT
    UUID userId = parseUserId(jwt);

    // Delegate update logic to the service
    Event updatedEvent = eventService.updateEventForOrganizer(userId, eventId, updateEventRequest);

    // Convert the updated entity → response DTO
    UpdateEventResponseDto updateEventResponseDto =
        eventMapper.toUpdateEventResponseDto(updatedEvent);

    // Return 200 OK with updated data
    return ResponseEntity.ok(updateEventResponseDto);
  }
}
