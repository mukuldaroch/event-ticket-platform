package com.daroch.tickets.repositories;

import com.daroch.tickets.domain.entities.QrCode;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QrCodeRepository extends JpaRepository<QrCode, UUID> {}
