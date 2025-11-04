package com.daroch.tickets.domain.dtos;

import com.daroch.tickets.domain.enums.EventStatusEnum;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventResponseDto {

  private UUID id;
  private String name;
  private String description;
  private LocalDateTime start;
  private LocalDateTime end;
  private String venue;
  private LocalDateTime salesStartDate;
  private LocalDateTime salesEndDate;
  private EventStatusEnum status;
  private List<UpdateTicketTypeResponseDto> ticketTypes;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
