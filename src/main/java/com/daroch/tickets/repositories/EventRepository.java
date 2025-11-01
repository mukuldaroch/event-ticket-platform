package com.daroch.tickets.repositories;

import com.daroch.tickets.domain.entities.Event;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {
  Page<Event> findByOrganizerId(UUID organiserId, Pageable pageable);

  Optional<Event> findByIdAndOrganizerId(UUID eventId, UUID organizerId);
}
