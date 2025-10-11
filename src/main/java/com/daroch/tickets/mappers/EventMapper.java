package com.daroch.tickets.mappers;

import com.daroch.tickets.domain.CreateEventRequest;
import com.daroch.tickets.domain.CreateTicketTypeRequest;
import com.daroch.tickets.domain.dtos.CreateEventRequestDto;
import com.daroch.tickets.domain.dtos.CreateEventResponseDto;
import com.daroch.tickets.domain.dtos.CreateTicketTypeRequestDto;
import com.daroch.tickets.domain.entities.Event;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {
  CreateTicketTypeRequest fromDto(CreateTicketTypeRequestDto dto);

  CreateEventRequest fromDto(CreateEventRequestDto dto);

  CreateEventResponseDto toDto(Event event);
}
