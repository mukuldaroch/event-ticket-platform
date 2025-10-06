package com.daroch.tickets.repositories;

import com.daroch.tickets.domain.entities.Staff;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends JpaRepository<Staff, UUID> {}
