package com.daroch.tickets.mappers;

import com.daroch.tickets.domain.CreateEventRequest;
import com.daroch.tickets.domain.CreateTicketTypeRequest;
import com.daroch.tickets.domain.UpdateEventRequest;
import com.daroch.tickets.domain.UpdateTicketTypeRequest;
import com.daroch.tickets.domain.dtos.CreateEventRequestDto;
import com.daroch.tickets.domain.dtos.CreateEventResponseDto;
import com.daroch.tickets.domain.dtos.CreateTicketTypeRequestDto;
import com.daroch.tickets.domain.dtos.GetEventDetailsResponseDto;
import com.daroch.tickets.domain.dtos.GetEventTicketTypesResponseDto;
import com.daroch.tickets.domain.dtos.ListEventResponseDto;
import com.daroch.tickets.domain.dtos.ListEventTicketTypeResponseDto;
import com.daroch.tickets.domain.dtos.ListPublisedEventResponseDto;
import com.daroch.tickets.domain.dtos.UpdateEventRequestDto;
import com.daroch.tickets.domain.dtos.UpdateEventResponseDto;
import com.daroch.tickets.domain.dtos.UpdateTicketTypeRequestDto;
import com.daroch.tickets.domain.dtos.UpdateTicketTypeResponseDto;
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

  // Put mapping
  UpdateTicketTypeRequest fromUpdateTicketTypeRequestDto(UpdateTicketTypeRequestDto dto);

  UpdateEventRequest fromUpdateEventRequestDto(UpdateEventRequestDto dto);

  UpdateTicketTypeResponseDto toUpdateTicketTypeResponseDto(TicketType ticketType);

  UpdateEventResponseDto toUpdateEventResponseDto(Event event);

  //
  ListPublisedEventResponseDto toListPublisedEventResponseDto(Event event);
}
