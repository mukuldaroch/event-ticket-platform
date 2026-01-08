package com.daroch.tickets.services.impl;

import com.daroch.tickets.domain.entities.QrCode;
import com.daroch.tickets.domain.entities.Ticket;
import com.daroch.tickets.domain.enums.QrCodeStatusEnum;
import com.daroch.tickets.exceptions.QrCodeGenerationException;
import com.daroch.tickets.repositories.QrCodeRepository;
import com.daroch.tickets.services.QrCodeService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;
import javax.imageio.ImageIO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service implementation responsible for generating QR codes linked to tickets. Uses ZXing to
 * generate a QR image and stores it as Base64 before persisting.
 */
@Service
@RequiredArgsConstructor
public class QrCodeServiceImpl implements QrCodeService {

  /** QR code writer instance from ZXing. */
  private final QRCodeWriter qrCodeWriter;

  /** Height of the generated QR image. */
  private static final int QR_HIGHT = 300;

  /** Width of the generated QR image. */
  private static final int QR_WIDTH = 300;

  /** Repository for persisting QR code entities. */
  private final QrCodeRepository qrCodeRepository;

  /**
   * Generates a Base64-encoded PNG QR code image for the given unique identifier.
   *
   * @param uniqueId UUID to encode inside the QR code.
   * @return Base64 String representing the generated PNG QR image.
   * @throws WriterException if ZXing fails to encode the QR data.
   * @throws IOException if writing to an in-memory stream fails.
   */
  private String generateQrCodeImage(UUID uniqueId) throws WriterException, IOException {
    // Generate matrix for the QR code
    BitMatrix bitMatrix =
        qrCodeWriter.encode(uniqueId.toString(), BarcodeFormat.QR_CODE, QR_WIDTH, QR_HIGHT);

    // Convert matrix to an actual image
    BufferedImage qrCodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

    // Convert BufferedImage → PNG → Base64
    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
      ImageIO.write(qrCodeImage, "PNG", baos);
      byte[] imageBytes = baos.toByteArray();
      return Base64.getEncoder().encodeToString(imageBytes);
    }
  }

  /**
   * Creates and persists a new QR code associated with a ticket. Generates a unique identifier,
   * turns it into a QR code image, stores both in the database.
   *
   * @param ticket the ticket to link the generated QR code to
   * @return saved QrCode entity containing the UUID, Base64 image, and status
   */
  @Override
  public QrCode generateQrCode(Ticket ticket) {
    try {
      // Create unique ID for the QR code
      UUID uniqueId = UUID.randomUUID();

      // Create Base64 QR code image
      String qrCodeImage = generateQrCodeImage(uniqueId);

      // Build QR code entity
      QrCode qrCode = new QrCode();
      qrCode.setId(uniqueId);
      qrCode.setStatus(QrCodeStatusEnum.ACTIVE);
      qrCode.setValue(qrCodeImage);
      qrCode.setTicket(ticket);

      // Persist and return
      return qrCodeRepository.saveAndFlush(qrCode);

    } catch (WriterException | IOException ex) {
      throw new QrCodeGenerationException("Failed to generate QR Code", ex);
    }
  }
}
