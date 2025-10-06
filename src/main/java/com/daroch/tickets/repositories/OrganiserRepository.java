package com.daroch.tickets.repositories;

import com.daroch.tickets.domain.entities.Organiser;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganiserRepository extends JpaRepository<Organiser, UUID> {}
