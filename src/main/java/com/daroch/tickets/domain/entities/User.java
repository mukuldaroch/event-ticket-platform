package com.daroch.tickets.domain.entities;

import jakarta.persistence.*;
import jakarta.persistence.EntityListeners;
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
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User {

  @Id
  @Column(name = "id", updatable = false, nullable = false)
  private UUID id; // This will match Keycloak user ID

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Column(name = "type", nullable = false)
  private String type;

  @CreatedDate
  @Column(name = "created", updatable = false, nullable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column(name = "updated", nullable = false)
  private LocalDateTime updatedAt;

  // ----------------------
  // USER can organise multiple EVENTS
  // ----------------------
  @OneToMany(mappedBy = "organizer", cascade = CascadeType.ALL)
  private List<Event> organizedEvents = new ArrayList<>();

  // ----------------------
  // USER can be STAFF in multiple EVENTS
  // ----------------------
  @ManyToMany
  @JoinTable(
      name = "user_event_staff",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "event_id"))
  private List<Event> eventStaff = new ArrayList<>();

  // ----------------------
  // USER can be ATTENDEE in multiple EVENTS
  // ----------------------
  @ManyToMany
  @JoinTable(
      name = "user_event_attendees",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "event_id"))
  private List<Event> eventAttendees = new ArrayList<>();

  // ----------------------
  // USER can have multiple TICKETS
  // ----------------------
  @OneToMany(mappedBy = "attendee", cascade = CascadeType.ALL)
  private List<Ticket> tickets = new ArrayList<>();

  // ----------------------
  // Optional: USER can purchase tickets
  // ----------------------
  // @OneToMany(mappedBy = "purchaser", cascade = CascadeType.ALL)
  // private List<Ticket> purchasedTickets = new ArrayList<>();

}
