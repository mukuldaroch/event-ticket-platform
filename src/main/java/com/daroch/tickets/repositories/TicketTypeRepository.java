package com.daroch.tickets.repositories;

import com.daroch.tickets.domain.entities.TicketType;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketTypeRepository extends JpaRepository<TicketType, UUID> {}
