package com.daroch.tickets.domain.entities;

import com.daroch.tickets.domain.enums.TicketStausEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "ticket")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Ticket {
  @Id
  @Column(name = "ticket_id", nullable = false, updatable = false)
  private UUID id;

  @Column(name = "ticket_status", nullable = false)
  @Enumerated(EnumType.STRING)
  private TicketStausEnum ticketStausEnum;

  @CreatedDate
  @Column(name = "created", updatable = false, nullable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column(name = "updated", nullable = false)
  private LocalDateTime updatedAt;

  // ----------------------
  // ----------------------
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "attendee_id")
  private Attendee attendee;

  // ----------------------
  // ----------------------
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ticket_type_id")
  private TicketType ticketType;

  // ----------------------
  // ----------------------
  // @ManyToOne(fetch = FetchType.LAZY)
  // @JoinColumn(name = "purchaser_id")
  // private Attendee purchaser;

  // ----------------------
  // ----------------------
  @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
  private List<TicketValidation> validations = new ArrayList<>();

  // ----------------------
  // ----------------------
  @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
  private List<QrCode> qrCodes = new ArrayList<>();
}
