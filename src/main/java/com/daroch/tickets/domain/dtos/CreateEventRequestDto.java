package com.daroch.tickets.domain.dtos;

import com.daroch.tickets.domain.enums.EventStatusEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEventRequestDto {

  @NotBlank(message = "Event name is required")
  private String name;

  private LocalDateTime start;

  private LocalDateTime end;

  @NotBlank(message = "venue information are required")
  private String venue;

  private LocalDateTime salesStartDate;

  private LocalDateTime salesEndDate;

  @NotNull(message = "Event status is required")
  private EventStatusEnum status;

  @NotEmpty(message = "At least one ticket type is required")
  @Valid
  private List<CreateTicketTypeRequestDto> ticketTypes;
}
// ---------------------------------------------------------------------
// This is the data you expect from the client when they call your API.
// ---------------------------------------------------------------------

// POST /events
// {
// "name": "Metal Concert",
// "start": "2025-12-31T20:00",
// "end": "2025-12-31T23:00",
// "venue": "Stadium",
// "salesStart": "2025-10-10T10:00",
// "salesEnd": "2025-12-30T23:59",
// "status": "UPCOMING",
// "ticketTypes": [
// { "name": "VIP", "price": 5000, "description": "Front row", "totalAvalaible":
// 50 },
// { "name": "Regular", "price": 1000, "description": "Standing",
// "totalAvalaible": 500 }
// ]
// }
