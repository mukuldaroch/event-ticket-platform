package com.daroch.tickets.domain.dtos;

import com.daroch.tickets.domain.enums.EventStatusEnum;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ListEventResponseDto {

  private UUID id;
  private String name;
  private LocalDateTime start;
  private LocalDateTime end;
  private String venue;
  private LocalDateTime salesStartDate;
  private LocalDateTime salesEndDate;
  private EventStatusEnum status;
  List<ListEventTicketTypeResponseDto> ticketTypes = new ArrayList<>();
}
