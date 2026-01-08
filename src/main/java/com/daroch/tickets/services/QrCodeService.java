package com.daroch.tickets.services;

import com.daroch.tickets.domain.entities.QrCode;
import com.daroch.tickets.domain.entities.Ticket;

public interface QrCodeService {
  QrCode generateQrCode(Ticket ticket);
}
