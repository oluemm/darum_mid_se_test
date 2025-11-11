package test.darum.authservice.model;

import jakarta.persistence.*;
import test.darum.authservice.enums.UserRole;

import java.util.UUID;

@Entity
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @Column(nullable = false, unique = true)
  private String email;
  
  @Column(nullable = false)
  private String password;
  
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private UserRole role;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public UserRole getRole() {
    return role;
  }

  public void setRole(UserRole role) {
    this.role = role;
  }
}
