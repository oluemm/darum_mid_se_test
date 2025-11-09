package test.darum.empmgmtsys.employee;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @NotNull
  @Column(name = "first_name")
  private String firstName;

  @NotNull
  @Column(name = "last_name")
  private String lastName;

  @NotNull
  @Column(unique = true)
  private String email;

  @NotNull
  private String status;

  @NotNull
  @Column(name = "created_at")
  private LocalDateTime createdAt;
}
