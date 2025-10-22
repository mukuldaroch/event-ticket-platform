package com.daroch.tickets.services;

import com.daroch.tickets.domain.CreateEventRequest;
import com.daroch.tickets.domain.entities.Event;
import java.util.UUID;

public interface EventService {
  Event createEvent(UUID organizerId, CreateEventRequest event);
}
