package com.daroch.tickets.domain.entities;

import jakarta.persistence.*;
import jakarta.persistence.EntityListeners;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User {

  @Id
  @Column(name = "id", updatable = false, nullable = false)
  private UUID id; // This will match Keycloak user ID

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "email", nullable = false, unique = true)
  private String email;

  // @Enumerated(EnumType.STRING)
  // @Column(name = "user_type", nullable = false)
  // private UserType userType; // ORGANISER, STAFF, ATTENDEE

  @CreatedDate
  @Column(name = "created", updatable = false, nullable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column(name = "updated", nullable = false)
  private LocalDateTime updatedAt;

  // ========================
  // Getters and Setters
  // ========================

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  // public UserType getUserType() {
  // return userType;
  // }
  //
  // public void setUserType(UserType userType) {
  // this.userType = userType;
  // }

  public LocalDateTime getCreated() {
    return createdAt;
  }

  public void setCreated(LocalDateTime created) {
    this.createdAt = created;
  }

  public LocalDateTime getUpdated() {
    return updatedAt;
  }

  public void setUpdated(LocalDateTime updated) {
    this.updatedAt = updated;
  }
}
