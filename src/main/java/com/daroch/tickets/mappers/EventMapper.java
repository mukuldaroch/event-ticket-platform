package com.daroch.tickets.mappers;

import com.daroch.tickets.domain.CreateEventRequest;
import com.daroch.tickets.domain.CreateTicketTypeRequest;
import com.daroch.tickets.domain.dtos.CreateEventRequestDto;
import com.daroch.tickets.domain.dtos.CreateEventResponseDto;
import com.daroch.tickets.domain.dtos.CreateTicketTypeRequestDto;
import com.daroch.tickets.domain.dtos.GetEventDetailsResponseDto;
import com.daroch.tickets.domain.dtos.GetEventTicketTypesResponseDto;
import com.daroch.tickets.domain.dtos.ListEventResponseDto;
import com.daroch.tickets.domain.dtos.ListEventTicketTypeResponseDto;
import com.daroch.tickets.domain.entities.Event;
import com.daroch.tickets.domain.entities.TicketType;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {
  // Post mapping
  CreateTicketTypeRequest fromDto(CreateTicketTypeRequestDto dto);

  CreateEventRequest fromDto(CreateEventRequestDto dto);

  CreateEventResponseDto toCreateEventResponseDto(Event event);

  // Get mapping
  ListEventTicketTypeResponseDto toListEventTicketTypeResponseDto(TicketType ticketType);

  ListEventResponseDto tolistEventResponseDto(Event event);

  GetEventTicketTypesResponseDto toGetEventTicketTypesResponseDtoDto(TicketType ticketType);

  GetEventDetailsResponseDto toGetEventDetailsResponseDto(Event event);
}
