package com.daroch.tickets.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daroch.tickets.domain.entities.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {

}
