package com.daroch.tickets.repositories;

import com.daroch.tickets.domain.entities.Attendee;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendeeRepository extends JpaRepository<Attendee, UUID> {}
