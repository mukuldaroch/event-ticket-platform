package com.daroch.tickets.domain.entities;

import com.daroch.tickets.domain.enums.EventStatusEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
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
@Table(name = "event")
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
public class Event {
  @Id
  @Column(name = "id", nullable = false, updatable = false)
  private UUID id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "start_time", nullable = false)
  private LocalDateTime start;

  @Column(name = "end_time", nullable = false)
  private LocalDateTime end;

  @Column(name = "venue", nullable = false)
  private String venue;

  @Column(name = "SaleStartDate")
  private LocalDateTime SaleStartDate;

  @Column(name = "SaleEndDate")
  private LocalDateTime SaleEndDate;

  @Column(name = "status", nullable = false)
  @Enumerated(EnumType.STRING)
  // for string representations to be stored in the database
  private EventStatusEnum status;

  @CreatedDate
  @Column(name = "created", updatable = false, nullable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column(name = "updated", nullable = false)
  private LocalDateTime updatedAt;

  // ----------------------
  // one ORGANISER can organize multiple EVENTS
  // ----------------------
  @ManyToOne
  @JoinColumn(name = "organiser_id")
  private Organiser organiser;

  // ----------------------
  // ----------------------
  @ManyToMany(mappedBy = "eventStaff")
  private List<Staff> staff = new ArrayList<>();

  // ----------------------
  // ----------------------
  @ManyToMany(mappedBy = "eventAttendees")
  private List<Attendee> attendees;

  // ----------------------
  // one EVENT can have multiple ATTENDEES
  // ----------------------
  @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
  private List<TicketType> ticketTypes = new ArrayList<>();
}
