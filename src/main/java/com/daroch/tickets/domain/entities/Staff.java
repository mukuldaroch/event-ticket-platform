package com.daroch.tickets.domain.entities;
import jakarta.persistence.EntityListeners;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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

@Entity
@Table(name = "staff")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Staff {
  @Id
  @Column(name = "id", updatable = false, nullable = false)
  private UUID id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "email", nullable = false)
  private String email;

  @CreatedDate
  @Column(name = "created", updatable = false, nullable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column(name = "updated", nullable = false)
  private LocalDateTime updatedAt;

  // ----------------------
  // ----------------------
  @ManyToMany()
  @JoinTable(
      name = "event_staff",
      joinColumns = @JoinColumn(name = "staff_id"),
      inverseJoinColumns = @JoinColumn(name = "event_id"))
  // joinColumns = staff’s FK
  // inverseJoinColumns = event’s FK
  private List<Event> eventStaff = new ArrayList<>();

  // The Core Idea of Many-to-Many:
  //
  // In relational databases, many-to-many is not directly possible.
  // You always need a join (bridge) table.
  //
  // Example:
  // One Event can have many Staff.
  // One Staff can work in many Events.
  // So we create a bridge table: event_staff(event_id, staff_id).
}
