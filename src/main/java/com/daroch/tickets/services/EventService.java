package com.daroch.tickets.services;

import java.util.UUID;

import com.daroch.tickets.domain.CreateEventRequest;
import com.daroch.tickets.domain.entities.Event;

public interface EventService {
	Event createEvent(UUID organizerId, CreateEventRequest event);
}
