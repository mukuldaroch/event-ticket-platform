package com.daroch.tickets.domain.entities;

import jakarta.persistence.*;
import jakarta.persistence.EntityListeners;
import java.time.LocalDateTime;
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
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User {

  @Id
  @Column(name = "id", updatable = false, nullable = false)
  private UUID id; // This will match Keycloak user ID

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @CreatedDate
  @Column(name = "created", updatable = false, nullable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column(name = "updated", nullable = false)
  private LocalDateTime updatedAt;

  @OneToOne(mappedBy = "user")
  private Organiser organiser;

  @OneToOne(mappedBy = "user")
  private Staff staff;

  @OneToOne(mappedBy = "user")
  private Attendee attendee;

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return Objects.equals(id, user.id)
        && Objects.equals(name, user.name)
        && Objects.equals(email, user.email)
        && Objects.equals(createdAt, user.createdAt)
        && Objects.equals(updatedAt, user.updatedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, email, createdAt, updatedAt);
  }
}
