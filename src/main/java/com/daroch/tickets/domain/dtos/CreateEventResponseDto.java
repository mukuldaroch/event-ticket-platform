package com.daroch.tickets.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.daroch.tickets.domain.enums.EventStatusEnum;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEventResponseDto {

	private UUID id;
	private String name;
	private String description;
	private Integer totalAvalaible;
	private LocalDateTime start;
	private LocalDateTime end;
	private String venue;
	private LocalDateTime salesStart;
	private LocalDateTime salesEnd;
	private EventStatusEnum status;
	private List<CreateTicketTypeRequestDto> ticketTypes;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
//---------------------------------------------------------------------
// This is the data you send back to the client after the API call.
//---------------------------------------------------------------------

// {
//   "id": "123e4567-e89b-12d3-a456-426614174000",
//   "name": "Metal Concert",
//   "start": "2025-12-31T20:00",
//   "end": "2025-12-31T23:00",
//   "venue": "Stadium",
//   "status": "UPCOMING",
//   "ticketTypes": [
//     { "id": "aaa111", "name": "VIP", "price": 5000 },
//     { "id": "bbb222", "name": "Regular", "price": 1000 }
//   ]
// }
