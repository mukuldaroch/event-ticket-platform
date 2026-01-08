package com.daroch.tickets.config;

import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QrCodeConfig {
  @Bean
  public QRCodeWriter qRCodeWriter() {
    return new QRCodeWriter();
  }
}
