package com.daroch.tickets.controllers;

import com.daroch.tickets.domain.dtos.ListPublisedEventResponseDto;
import com.daroch.tickets.mappers.EventMapper;
import com.daroch.tickets.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/published-events")
@RequiredArgsConstructor
public class PublishedEventController {
  private final EventService eventService;
  private final EventMapper eventMapper;

  @GetMapping
  public ResponseEntity<Page<ListPublisedEventResponseDto>> listPublishedEvents(Pageable pageable) {
    Page<ListPublisedEventResponseDto> page =
        eventService.listPublishedEvents(pageable).map(eventMapper::toListPublisedEventResponseDto);

    return ResponseEntity.ok(page);
  }
}
