package com.daroch.tickets.services.impl;

import com.daroch.tickets.domain.entities.Ticket;
import com.daroch.tickets.repositories.TicketRepository;
import com.daroch.tickets.repositories.TicketTypeRepository;
import com.daroch.tickets.repositories.UserRepository;
import com.daroch.tickets.services.QrCodeService;
import com.daroch.tickets.services.TicketTypeService;
import jakarta.transaction.Transactional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketTypeServiceImpl implements TicketTypeService {
  private final UserRepository userRepository;
  private final TicketRepository ticketRepository;
  private final TicketTypeRepository ticketTypeRepository;
  private final QrCodeService qrCodeService;

  @Override
  @Transactional
  public Ticket purchaseTicket(UUID userId, UUID ticketTypeId) {
    return null;
  }
}
