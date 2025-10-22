package com.daroch.tickets.domain.entities;

import com.daroch.tickets.domain.enums.EventStatusEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
public class Event {

  @Id
  @Column(name = "id", nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.UUID)
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

  // ---------------------------------------------
  // multiple EVENTS can be organised by ORGANISER
  // ---------------------------------------------
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "organiser_id")
  private User organiser;

  // ---------------------------------------------
  // EVENT can have multiple STAFF
  // ---------------------------------------------
  @ManyToMany(mappedBy = "eventStaff")
  private List<User> staff = new ArrayList<>();

  // ---------------------------------------------
  // EVENT can have multiple ATTENDEES
  // ---------------------------------------------
  @ManyToMany(mappedBy = "eventAttendees")
  private List<User> attendees;

  // ---------------------------------------------
  // EVENT can have multiple TicketTypes
  // ---------------------------------------------
  @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<TicketType> ticketTypes = new ArrayList<>();

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Event event = (Event) o;
    return Objects.equals(id, event.id)
        && Objects.equals(name, event.name)
        && Objects.equals(start, event.start)
        && Objects.equals(end, event.end)
        && Objects.equals(venue, event.venue)
        && Objects.equals(SaleStartDate, event.SaleStartDate)
        && Objects.equals(SaleEndDate, event.SaleEndDate)
        && status == event.status
        && Objects.equals(createdAt, event.createdAt)
        && Objects.equals(updatedAt, event.updatedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        id, name, start, end, venue, SaleStartDate, SaleEndDate, status, createdAt, updatedAt);
  }
}
