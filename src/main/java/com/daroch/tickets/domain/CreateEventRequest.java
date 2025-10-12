package com.daroch.tickets.domain;

import com.daroch.tickets.domain.enums.EventStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateEventRequest {
	private String name;
	private LocalDateTime start;
	private LocalDateTime end;
	private String venue;
	private LocalDateTime salesStart;
	private LocalDateTime salesEnd;
	private EventStatusEnum status;
	private List<CreateTicketTypeRequest> ticketTypes = new ArrayList<>();
}
// POST /events
// {
// "name": "Music Concert",
// "start": "2025-12-01T18:00:00",
// "end": "2025-12-01T21:00:00",
// "venue": "City Hall",
// "salesStart": "2025-11-01T00:00:00",
// "salesEnd": "2025-11-30T23:59:00",
// "status": "ACTIVE",
// "organiserId": 5,
// "ticketTypes": [
// { "name": "VIP", "price": 150.0, "description": "Best seats",
// "totalAvailable": 50 },
// { "name": "Regular", "price": 50.0, "description": "Standard seat",
// "totalAvailable": 200 }
// ]
// }
