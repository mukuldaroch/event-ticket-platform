package com.daroch.tickets.services.impl;

import com.daroch.tickets.domain.CreateEventRequest;
import com.daroch.tickets.domain.entities.Event;
import com.daroch.tickets.domain.entities.TicketType;
import com.daroch.tickets.domain.entities.User;
import com.daroch.tickets.exceptions.UserNotFoundException;
import com.daroch.tickets.repositories.EventRepository;
import com.daroch.tickets.repositories.UserRepository;
import com.daroch.tickets.services.EventService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

  private final UserRepository userRepository;
  private final EventRepository eventRepository;

  // -------------------------------------------------------------
  // -------------------------------------------------------------
  @Override
  public Event createEvent(UUID organizerId, CreateEventRequest eventRequest) {

    // 1: Find organiser
    User organizer =
        userRepository
            .findById(organizerId)
            .orElseThrow(
                () ->
                    new UserNotFoundException(
                        String.format("User with ID '%s' not found", organizerId)));

    // 3: Create Event entity
    Event eventToCreate = new Event();

    // 2: Map CreateTicketTypeRequest → TicketType entities
    List<TicketType> ticketTypesToCreate =
        eventRequest.getTicketTypes().stream()
            .map(
                ticketType -> {
                  // ticketType is a lambda parameter — basically a
                  // placeholder name for each element of the stream. Think of
                  // it like a loop variable in a for loop:
                  TicketType ticketTypeEntity = new TicketType();
                  ticketTypeEntity.setName(ticketType.getName());
                  ticketTypeEntity.setPrice(ticketType.getPrice());
                  ticketTypeEntity.setDescription(ticketType.getDescription());
                  ticketTypeEntity.setTotalAvailable(ticketType.getTotalAvailable());
                  ticketTypeEntity.setEvent(eventToCreate);
                  return ticketTypeEntity;
                })
            .toList();
    // for (CreateTicketTypeRequest ticketType :
    // eventRequest.getTicketTypes()) {}

    eventToCreate.setName(eventRequest.getName());
    eventToCreate.setStart(eventRequest.getStart());
    eventToCreate.setEnd(eventRequest.getEnd());
    eventToCreate.setVenue(eventRequest.getVenue());
    eventToCreate.setSalesStartDate(eventRequest.getSalesStartDate());
    eventToCreate.setSalesEndDate(eventRequest.getSalesEndDate());
    eventToCreate.setStatus(eventRequest.getStatus());
    eventToCreate.setOrganizer(organizer);
    // 4: Linking the relationship
    eventToCreate.setTicketTypes(ticketTypesToCreate);

    // 5: Save the event (cascades tickets automatically)
    return eventRepository.save(eventToCreate);
  }

  // -------------------------------------------------------------
  // -------------------------------------------------------------
  @Override
  public Page<Event> listEventsForOrganizer(UUID organizerId, Pageable pageable) {
    return eventRepository.findByOrganizerId(organizerId, pageable);
  }

  // -------------------------------------------------------------
  // -------------------------------------------------------------
  @Override
  public Optional<Event> getEventForOrganizer(UUID organizerId, UUID eventId) {
    return eventRepository.findByIdAndOrganizerId(eventId, organizerId);
  }
}
