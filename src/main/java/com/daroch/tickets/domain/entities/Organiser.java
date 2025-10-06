package com.daroch.tickets.domain.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "organiser")
@EntityListeners(AuditingEntityListener.class)
public class Organiser {
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

  @OneToMany(mappedBy = "organiser", cascade = CascadeType.ALL)
  private List<Event> organizedEvents = new ArrayList<>();

  // ====== Constructors ======
  public Organiser() {}

  public Organiser(
      UUID id,
      String name,
      String email,
      LocalDateTime created,
      LocalDateTime updated,
      List<Event> organizedEvents) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.createdAt = created;
    this.updatedAt = updated;
    this.organizedEvents = organizedEvents;
  }

  // ====== Getters ======
  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public LocalDateTime getCreated() {
    return createdAt;
  }

  public LocalDateTime getUpdated() {
    return updatedAt;
  }

  public List<Event> getOrganizedEvents() {
    return organizedEvents;
  }

  // ====== Setters ======
  public void setId(UUID id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setCreated(LocalDateTime created) {
    this.createdAt = created;
  }

  public void setUpdated(LocalDateTime updated) {
    this.updatedAt = updated;
  }

  public void setOrganizedEvents(List<Event> organizedEvents) {
    this.organizedEvents = organizedEvents;
  }
}
