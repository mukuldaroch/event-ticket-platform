package com.daroch.tickets.services.impl;

import com.daroch.tickets.domain.CreateEventRequest;
import com.daroch.tickets.domain.UpdateEventRequest;
import com.daroch.tickets.domain.UpdateTicketTypeRequest;
import com.daroch.tickets.domain.entities.Event;
import com.daroch.tickets.domain.entities.TicketType;
import com.daroch.tickets.domain.entities.User;
import com.daroch.tickets.exceptions.EventNotFoundException;
import com.daroch.tickets.exceptions.EventUpdateException;
import com.daroch.tickets.exceptions.TicketTypeNotFoundException;
import com.daroch.tickets.exceptions.UserNotFoundException;
import com.daroch.tickets.repositories.EventRepository;
import com.daroch.tickets.repositories.UserRepository;
import com.daroch.tickets.services.EventService;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

  private final UserRepository userRepository;
  private final EventRepository eventRepository;

  /**
   * Creates a new event for the given organizer.
   *
   * @param organizerId ID of the organizer creating the event
   * @param eventRequest request payload containing event + ticket details
   * @return saved Event entity
   */
  @Override
  @Transactional
  public Event createEvent(UUID organizerId, CreateEventRequest eventRequest) {

    // Find the organizer (must exist, or throw exception)
    User organizer =
        userRepository
            .findById(organizerId)
            .orElseThrow(
                () ->
                    new UserNotFoundException(
                        String.format("User with ID '%s' not found", organizerId)));

    // Prepare TicketType entities from the incoming request
    // Basically: convert each CreateTicketTypeRequest into a TicketType
    // entity
    Event eventToCreate = new Event();

    List<TicketType> ticketTypesToCreate =
        eventRequest.getTicketTypes().stream()
            .map(
                ticketType -> {
                  TicketType ticketTypeEntity = new TicketType();
                  ticketTypeEntity.setName(ticketType.getName());
                  ticketTypeEntity.setPrice(ticketType.getPrice());
                  ticketTypeEntity.setDescription(ticketType.getDescription());
                  ticketTypeEntity.setTotalAvailable(ticketType.getTotalAvailable());
                  ticketTypeEntity.setEvent(eventToCreate); // link back to
                  // parent Event
                  return ticketTypeEntity;
                })
            .toList();

    // Map event-level details from the request
    eventToCreate.setName(eventRequest.getName());
    eventToCreate.setStart(eventRequest.getStart());
    eventToCreate.setEnd(eventRequest.getEnd());
    eventToCreate.setVenue(eventRequest.getVenue());
    eventToCreate.setSalesStartDate(eventRequest.getSalesStartDate());
    eventToCreate.setSalesEndDate(eventRequest.getSalesEndDate());
    eventToCreate.setStatus(eventRequest.getStatus());
    eventToCreate.setOrganizer(organizer);
    eventToCreate.setTicketTypes(ticketTypesToCreate);

    // Save the event (cascades and saves ticket types automatically)
    return eventRepository.save(eventToCreate);
  }

  /**
   * Lists all events created by a specific organizer, paginated.
   *
   * @param organizerId organizer's ID
   * @param pageable pagination settings
   * @return Page of events
   */
  @Override
  public Page<Event> listEventsForOrganizer(UUID organizerId, Pageable pageable) {
    return eventRepository.findByOrganizerId(organizerId, pageable);
  }

  /**
   * Retrieves a single event for an organizer (if it belongs to them).
   *
   * @param organizerId organizer's ID
   * @param eventId event ID
   * @return Optional<Event> (empty if not found or not owned)
   */
  @Override
  public Optional<Event> getEventForOrganizer(UUID organizerId, UUID eventId) {
    return eventRepository.findByIdAndOrganizerId(eventId, organizerId);
  }

  /**
   * Updates an event and its associated ticket types.
   *
   * <p>Handles 3 cases for ticket types: - Create new ticket types (no ID) - Update existing ones
   * (ID exists) - Delete ones missing from request
   *
   * @param organizerId organizer's ID
   * @param eventId event ID to update
   * @param event update request payload
   * @return updated Event
   */
  @Override
  @Transactional
  public Event updateEventForOrganizer(UUID organizerId, UUID eventId, UpdateEventRequest event) {

    // Validation: request must contain matching IDs
    if (null == event.getId()) {
      throw new EventUpdateException("Event ID cannot be null");
    }
    if (!eventId.equals(event.getId())) {
      throw new EventUpdateException("Cannot update the ID of an event");
    }

    // 1️⃣ Fetch the existing event (must belong to the same organizer)
    Event existingEvent =
        eventRepository
            .findByIdAndOrganizerId(eventId, organizerId)
            .orElseThrow(
                () ->
                    new EventNotFoundException(
                        String.format("Event with id '%s' does not exist", eventId)));

    // Update base event info
    existingEvent.setName(event.getName());
    existingEvent.setStart(event.getStart());
    existingEvent.setEnd(event.getEnd());
    existingEvent.setVenue(event.getVenue());
    existingEvent.setSalesStartDate(event.getSalesStartDate());
    existingEvent.setSalesEndDate(event.getSalesEndDate());
    existingEvent.setStatus(event.getStatus());

    // Collect IDs of ticket types from the update request
    Set<UUID> requestTicketTypeIds =
        event.getTicketTypes().stream()
            .map(UpdateTicketTypeRequest::getId)
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());

    // Delete ticket types that are missing in the new request
    existingEvent
        .getTicketTypes()
        .removeIf(existingTicketType -> !requestTicketTypeIds.contains(existingTicketType.getId()));

    // Create an index (map) of existing ticket types for fast lookup
    Map<UUID, TicketType> existingTicketTypesIndex =
        existingEvent.getTicketTypes().stream()
            .collect(Collectors.toMap(TicketType::getId, Function.identity()));

    // Loop through request ticket types — create/update accordingly
    for (UpdateTicketTypeRequest ticketType : event.getTicketTypes()) {
      if (null == ticketType.getId()) {

        // Create new ticket type
        TicketType ticketTypeEntity = new TicketType();
        ticketTypeEntity.setName(ticketType.getName());
        ticketTypeEntity.setPrice(ticketType.getPrice());
        ticketTypeEntity.setDescription(ticketType.getDescription());
        ticketTypeEntity.setTotalAvailable(ticketType.getTotalAvailable());
        ticketTypeEntity.setEvent(existingEvent);
        existingEvent.getTicketTypes().add(ticketTypeEntity);

      } else if (existingTicketTypesIndex.containsKey(ticketType.getId())) {
        // Update existing ticket type
        TicketType existingTicketType = existingTicketTypesIndex.get(ticketType.getId());
        existingTicketType.setName(ticketType.getName());
        existingTicketType.setPrice(ticketType.getPrice());
        existingTicketType.setDescription(ticketType.getDescription());
        existingTicketType.setTotalAvailable(ticketType.getTotalAvailable());

      } else {
        // ID not found → invalid ticket type reference
        throw new TicketTypeNotFoundException(
            String.format("Ticket type with ID '%s' does not exist", ticketType.getId()));
      }
    }

    // save the updated event (cascade saves ticket types)
    return eventRepository.save(existingEvent);
  }
}
