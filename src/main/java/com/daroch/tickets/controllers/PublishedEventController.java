package com.daroch.tickets.controllers;

import com.daroch.tickets.domain.dtos.GetPublishedEventDetailsResponseDto;
import com.daroch.tickets.domain.dtos.ListPublisedEventResponseDto;
import com.daroch.tickets.domain.entities.Event;
import com.daroch.tickets.mappers.EventMapper;
import com.daroch.tickets.services.EventService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/published-events")
@RequiredArgsConstructor
public class PublishedEventController {
  private final EventService eventService;
  private final EventMapper eventMapper;

  @GetMapping
  public ResponseEntity<Page<ListPublisedEventResponseDto>> listPublishedEvents(
      @RequestBody(required = false) String q, Pageable pageable) {

    Page<Event> events;
    if (q != null && !q.trim().isEmpty()) {
      events = eventService.serachPublishedEvents(q, pageable);

    } else {
      events = eventService.listPublishedEvents(pageable);
    }

    return ResponseEntity.ok(events.map(eventMapper::toListPublisedEventResponseDto));
  }

  @GetMapping("/{eventId}")
  public ResponseEntity<GetPublishedEventDetailsResponseDto> getPublishedEventDetails(
      @PathVariable UUID eventId) {
    return eventService
        .getPublishedEvents(eventId)
        .map(eventMapper::toGetPublishedEventDetailsResponseDto)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }
}
