package com.daroch.tickets.services.impl;

import com.daroch.tickets.domain.CreateEventRequest;
import com.daroch.tickets.domain.UpdateEventRequest;
import com.daroch.tickets.domain.UpdateTicketTypeRequest;
import com.daroch.tickets.domain.entities.Event;
import com.daroch.tickets.domain.entities.TicketType;
import com.daroch.tickets.domain.entities.User;
import com.daroch.tickets.domain.enums.EventStatusEnum;
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
   * Creates a new event for a given organizer.
   *
   * <p>Sets up the event entity and its associated ticket types, linking each ticket type to the
   * parent event. The event and tickets are saved atomically via cascading.
   *
   * @param organizerId the UUID of the organizer creating the event; must not be null
   * @param eventRequest payload containing event details and ticket type information
   * @return the saved Event entity with ticket types
   * @throws UserNotFoundException if no user exists with the given organizerId
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
   * Retrieves a paginated list of events created by a specific organizer.
   *
   * @param organizerId the UUID of the organizer; must not be null
   * @param pageable pagination and sorting information
   * @return a Page of Event entities created by the organizer
   */
  @Override
  public Page<Event> listEventsForOrganizer(UUID organizerId, Pageable pageable) {
    return eventRepository.findByOrganizerId(organizerId, pageable);
  }

  /**
   * Retrieves a single event belonging to a specific organizer.
   *
   * @param organizerId the UUID of the organizer; must not be null
   * @param eventId the UUID of the event to fetch; must not be null
   * @return an Optional containing the Event if found and owned by the organizer, otherwise empty
   */
  @Override
  public Optional<Event> getEventForOrganizer(UUID organizerId, UUID eventId) {
    return eventRepository.findByIdAndOrganizerId(eventId, organizerId);
  }

  /**
   * Updates an existing event and its associated ticket types for a specific organizer.
   *
   * <p>Handles three cases for ticket types: creation of new ones (no ID), updating existing ones
   * (matching ID), and deletion of ticket types missing from the request.
   *
   * @param organizerId the UUID of the organizer; must not be null
   * @param eventId the UUID of the event to update; must match the ID in the request
   * @param event the update request payload containing updated event and ticket type data
   * @return the updated Event entity
   * @throws EventUpdateException if the request contains a null ID or mismatched event ID
   * @throws EventNotFoundException if no event exists with the given eventId for the organizer
   * @throws TicketTypeNotFoundException if a ticket type in the request does not exist in the event
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

  /**
   * Deletes an event belonging to a specific organizer.
   *
   * <p>Performs a safe deletion by combining the event ID and organizer ID, ensuring that only
   * events belonging to the given organizer are deleted. If no event is deleted, an exception is
   * thrown.
   *
   * @param organizerId the UUID of the organizer; must not be null
   * @param eventId the UUID of the event to delete; must not be null
   * @throws IllegalArgumentException if either organizerId or eventId is null
   * @throws RuntimeException if no event was deleted (event not found or not owned by organizer)
   */
  @Override
  public void deleteEventForOrganizer(UUID organizerId, UUID eventId) {
    getEventForOrganizer(organizerId, eventId).ifPresent(eventRepository::delete);
  }

  /**
   * Retrieves a paginated list of all events that are in PUBLISHED status.
   *
   * <p>This is used for publicly visible listings where only published events should be displayed.
   *
   * @param pageable pagination details such as page number and size
   * @return a paginated list of published events
   */
  @Override
  public Page<Event> listPublishedEvents(Pageable pageable) {
    return eventRepository.findByStatus(EventStatusEnum.PUBLISHED, pageable);
  }

  /**
   * Searches published events using a text-based query.
   *
   * <p>The search is delegated to the repository where full-text or LIKE-based search may be
   * implemented, and only events that are published are returned.
   *
   * @param query the search keyword to match against event fields
   * @param pageable pagination details for the result list
   * @return a paginated list of search results within published events
   */
  @Override
  public Page<Event> serachPublishedEvents(String query, Pageable pageable) {
    return eventRepository.searchEvents(query, pageable);
  }

  /**
   * Retrieves a published event by its ID.
   *
   * <p>This ensures that only events in PUBLISHED status are returned. If the event exists but is
   * not published, the result will be empty.
   *
   * @param eventId the UUID of the event to fetch
   * @return an Optional containing the published event if found, otherwise empty
   */
  @Override
  public Optional<Event> getPublishedEvents(UUID eventId) {
    return eventRepository.findByIdAndStatus(eventId, EventStatusEnum.PUBLISHED);
  }
}
