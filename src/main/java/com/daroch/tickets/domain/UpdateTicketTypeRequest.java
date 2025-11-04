package com.daroch.tickets.domain;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTicketTypeRequest {
  private UUID id;
  private String name;
  private Double price;
  private String description;
  private Integer totalAvailable;
}
